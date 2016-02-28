(ns grimoire.web.routes
  (:require [clojure.string :as string]
            [clojure.core.match :refer [match]]
            [compojure.core :refer [defroutes context GET let-routes routing]]
            [compojure.route :as route]
            [grimoire.util :as util]
            [grimoire.api :as api]
            [grimoire.web.util :as wutil]
            [grimoire.things :as t]
            [grimoire.either :as either]
            [grimoire.web.config :as cfg]
            [grimoire.web.views :as v]
            [grimoire.web.views.content.html :as v.c.h]
            [grimoire.web.views.content.cheatsheet :as v.c.cs]
            [grimoire.web.views.errors :as v.e]
            [grimoire.web.views.api :as v.api]
            [grimoire.web.views.autocomplete :as v.auto]
            [ring.util.response :as response]
            [simpledb.core :as sdb]
            [taoensso.timbre :as timbre :refer [warn]]))

(def ^:private platform-regex
  #"clj|cljs|cljclr|pixi|kiss|ox|toc")

(def ^:private privilaged-user-agents
  #{"URL/Emacs"})

(defn ^:private bot? [uas]
  (or (re-find #"[Bb]ot" uas)
      (re-find #"[Ss]pider" uas)))

(def ^:private incf (fnil inc 0))

(defn- log! [request thing]
  ;; FIXME: could be one transaction
  
  ;; what do I want to know
  ;; - inc user-agent string tracking
  (let [user-agent (get-in request [:headers "user-agent"] "Unknown")
        db (cfg/simpledb-config)]
    (when-not (bot? user-agent)
      (sdb/update! db
                   :clients update
                   user-agent incf)

      ;; - inc the thing URI in the "thing"
      (when thing
        ;; - if def, inc in the def store at ":artifact/:ns/:def"
        (when (t/def? thing)
          (sdb/update! db
                       :defs update
                       (t/thing->short-string thing) incf))

        (when-let [ns (t/thing->namespace thing)]
          (sdb/update! db
                       :namespaces update
                       (t/thing->short-string ns) incf))

        (when-let [platform (t/thing->platform thing)]
          (sdb/update! db
                       :platforms update
                       (t/thing->name platform) incf))
        (when (t/artifacted? thing)
          (let [artifact (t/thing->artifact thing)
                group    (t/thing->group    artifact)]
            (when artifact
              (sdb/update! db
                           :artifacts update
                           (str (t/thing->name group) \/ (t/thing->name artifact)) incf))))))))

(defn store-v1
  [{header-type :content-type
    {param-type :type} :params
    :as req
    uri :uri}]
  (let [req (assoc req :content-type
                   (-> (or header-type
                           param-type
                           :html)
                       wutil/normalize-type))
        log-msg (pr-str {:uri        uri
                         :type       type
                         :user-agent (get-in req [:headers "user-agent"])})]
    (->> (context "/store/v1" []
           (GET "/" {uri :uri}
             (when-let [r (v/store-page req)]
               (log! req nil)
               r))

           (context "/:groupid" [groupid]
             (let-routes [t (t/->Group groupid)]
               (GET "/" []
                 (when-let [r (v/group-page req t)]
                   (log! req t)
                   r))

               (context "/:artifactid" [artifactid]
                 (let-routes [t (t/->Artifact t artifactid)]
                   (GET "/" []
                     (when-let [r (v/artifact-page req t)]
                       (log! req t)
                       r))

                   (context "/:version" [version]
                     (let-routes [t (t/->Version t version)]
                       (GET "/" []
                         (when-let [r (v/version-page req t)]
                           (log! req t)
                           r))

                       (context ["/:platform"
                                 :platform platform-regex] [platform]
                         (let-routes [t (t/->Platform t platform)]
                           (GET "/" []
                             (when-let [r (v/platform-page req t)]
                               (log! req t)
                               r))

                           (context "/:namespace" [namespace]
                             (let-routes [t (t/->Ns t namespace)]
                               (GET "/" []
                                 (when-let [r (v/namespace-page req t)]
                                   (log! req t)
                                   r))

                               (context "/:symbol" [symbol]
                                 (let-routes [t (t/->Def t symbol)]
                                   (GET "/" []
                                     (when-let [r (v/symbol-page req t)]
                                       (log! req t)
                                       r))

                                   (route/not-found
                                    (fn [req]
                                      (warn log-msg)
                                      (v.e/error-unknown-symbol req t)))))

                               (route/not-found
                                (fn [req]
                                  (warn log-msg)
                                  (v.e/error-unknown-namespace t)))))

                           (route/not-found
                            (fn [req]
                              (warn log-msg)
                              (v.e/error-unknown-platform t)))))

                       (route/not-found
                        (fn [req]
                          (warn log-msg)
                          (v.e/error-unknown-version t)))))

                   (route/not-found
                    (fn [req]
                      (warn log-msg)
                      (v.e/error-unknown-artifact t)))))

               (route/not-found
                (fn [req]
                  (warn log-msg)
                  (v.e/error-unknown-group t))))))

         (routing req))))

(defmacro api-log []
  `(log! ~'req ~'t))

(defmacro do-dispatch
  [dispatch type op t]
  `(or (when-let [f# (~dispatch ~op)]
         (when-let [r# (f# ~type ~t)]
           (log! ~'req ~'t)
           r#))
       (do (warn ~'log-msg)
           (v.api/unknown-op ~type ~op ~t))))

(def api-v0
  (fn [{header-type :content-type
        {param-type :type
         op         :op :as params} :params
        :as req
        uri :uri}]
    (->> (let-routes [t       nil
                      type    (wutil/normalize-type
                               (or header-type
                                   param-type
                                   :json))
                      log-msg (pr-str {:uri        uri
                                       :type       type
                                       :op         op
                                       :user-agent (get-in req [:headers "user-agent"])})]
           (context ["/api/v0"] []
             (GET "/" []
               (do-dispatch v.api/root-ops
                            type op params))

             (context ["/:group"] [group]
               (let-routes [t (t/->Group group)]
                 (GET "/" []
                   (do-dispatch v.api/group-ops
                                type op t))

                 (context ["/:artifact"] [artifact]
                   (let-routes [t (t/->Artifact t artifact)]
                     (GET "/" []
                       (do-dispatch v.api/artifact-ops
                                    type op t))

                     (context ["/:version"] [version]
                       (let-routes [t (t/->Version t version)]
                         (GET "/" []
                           (do-dispatch v.api/version-ops
                                        type op t))

                         (context ["/:namespace"] [namespace]
                           (let-routes [t (-> t
                                              (t/->Platform "clj")
                                              (t/->Ns namespace))]
                             (GET "/" []
                               (do-dispatch v.api/namespace-ops
                                            type op t))

                             (context ["/:symbol"] [symbol]
                               (let-routes [t (t/->Def t symbol)]
                                 (GET "/" []
                                   (do-dispatch v.api/def-ops
                                                type op t))))))))))))))
         (routing req))))

(def api-v2
  (fn [{header-type :content-type
        {param-type :type
         op         :op :as params} :params
        :as req
        uri :uri}]
    (->> (let-routes [t       nil
                      type    (wutil/normalize-type
                               (or header-type
                                   param-type
                                   :json))
                      log-msg (pr-str {:uri        uri
                                       :type       type
                                       :op         op
                                       :user-agent (get-in req [:headers "user-agent"])})]
           (context ["/api/v2"] []
             (GET "/" []
               (do-dispatch v.api/root-ops
                            type op params))

             (context ["/:group"] [group]
               (let-routes [t (t/->Group group)]
                 (GET "/" []
                   (do-dispatch v.api/group-ops
                                type op t))

                 (context ["/:artifact"] [artifact]
                   (let-routes [t (t/->Artifact t artifact)]
                     (GET "/" []
                       (do-dispatch v.api/artifact-ops
                                    type op t))

                     (context ["/:version"] [version]
                       (let-routes [t (t/->Version t version)]
                         (GET "/" []
                           (do-dispatch v.api/version-ops
                                        type op t))

                         (context ["/:platform"] [platform]
                           (let-routes [t (t/->Platform t platform)]

                             (GET "/" []
                               (do-dispatch v.api/platform-ops
                                            type op t))

                             (context ["/:namespace"] [namespace]
                               (let-routes [t (t/->Ns t namespace)]
                                 (GET "/" []
                                   (do-dispatch v.api/namespace-ops
                                                type op t))

                                 (context ["/:symbol"] [symbol]
                                   (let-routes [t (t/->Def t symbol)]
                                     (GET "/" []
                                       (do-dispatch v.api/def-ops
                                                    type op t))))))))))))))))
         (routing req))))

(declare app)

(defn search
  [{header-type :content-type
    {param-type :type} :params
    :as req uri :uri}]
  (let [type    (-> (or header-type param-type :html)
                    wutil/normalize-type)
        log-msg (pr-str {:uri        uri
                         :type       type
                         :user-agent (get-in req [:headers "user-agent"])})]
    (->> (context "/search" []
           (context "/v0" []
             (context "/:ns" [ns]
               (context "/:symbol" [symbol]
                 (fn [request]
                   (when-let [v-thing ((v/ns-version-index) ns)]
                     (let [user-agent (get-in request [:headers "user-agent"])
                           new-uri    (format "/store/v0/%s/%s/%s/%s/%s/%s"
                                              (t/thing->name (t/thing->group v-thing))
                                              (t/thing->name (t/thing->artifact v-thing))
                                              (t/thing->name v-thing)
                                              "clj"
                                              ns
                                              symbol)
                           new-req    (-> request
                                          (assoc :uri new-uri)
                                          (dissoc :context :path-info))]
                       (log! req nil)
                       (if (privilaged-user-agents user-agent)
                         (#'app new-req) ;; pass it forwards
                         (response/redirect new-uri 307))))))

               (route/not-found
                (fn [req]
                  (warn log-msg)
                  (v.e/search-no-symbol type "v0" ns))))

             (route/not-found
              (fn [req]
                (warn log-msg)
                (v.e/search-no-version type "v0"))))

           (context "/v1" []
             (context "/:platform" [platform]
               (context "/:ns" [ns]
                 (context "/:symbol" [symbol]
                   (fn [request]
                     (when-let [v-thing ((v/ns-version-index) ns)]
                       (let [user-agent (get-in request [:headers "user-agent"])
                             new-uri    (format "/store/v0/%s/%s/%s/%s/%s/%s"
                                                (t/thing->name (t/thing->group v-thing))
                                                (t/thing->name (t/thing->artifact v-thing))
                                                (t/thing->name v-thing)
                                                platform
                                                ns
                                                symbol)
                             new-req    (-> request
                                            (assoc :uri new-uri)
                                            (dissoc :context :path-info))]
                         (log! req nil)
                         (if (privilaged-user-agents user-agent)
                           (#'app new-req) ;; pass it forwards
                           (response/redirect new-uri 307))))))

                 (route/not-found
                  (fn [req]
                    (warn log-msg)
                    (v.e/search-no-symbol type "v1" ns))))

               (route/not-found
                (fn [req]
                  (warn log-msg)
                  (v.e/search-no-version type "v1")))))

           (GET "/autocomplete" {{query :query} :params}
             (v.auto/complete query)))

         (routing req))))

(declare app)

(defn upgrade-unstored-req [request _]
  (let [user-agent (get-in request [:headers "user-agent"])
        uri        (:uri request)
        path       (rest (string/split uri #"/"))
        new-uri    (match [path]
                     [([version ns name] :seq)]
                     ,,(str "/store/v0/org.clojure/clojure/" version "/clj/" ns "/" name)
                     
                     [([version ns] :seq)]
                     ,,(str "/store/v0/org.clojure/clojure/" version "/clj/" ns)
                     
                     [([version] :seq)]
                     ,,(str "/store/v0/org.clojure/clojure/" version))
        new-req    (-> request
                       (assoc :uri new-uri)
                       (dissoc :context :path-info))]
    (if (privilaged-user-agents user-agent)
      (#'app new-req) ;; pass it forwards
      (wutil/moved-permanently new-uri))))

(defn upgrade-unversioned-store-req
  [request group]
  (let [user-agent (get-in request [:headers "user-agent"])
        ;; FIXME: Forged URI doesn't have a platform part
        tail       (:path-info request)
        new-uri    (str "/store/v0/"
                        group
                        tail)
        new-req    (-> request
                       (assoc :uri new-uri)
                       (dissoc :context :path-info))]
    (if (privilaged-user-agents user-agent)
      (#'app new-req) ;; pass it forwards
      (wutil/moved-permanently new-uri))))

(defn rewrite-latest->version-req
  [request store-v group artifact]
  (let [user-agent (get-in request [:headers "user-agent"])
        t          (-> (cfg/lib-grim-config)
                       (t/->Group group)
                       (t/->Artifact artifact))
        versions   (-> (cfg/lib-grim-config)
                       (api/list-versions t)
                       either/result)
        artifact-v (first versions)
        uri        (:uri request)
        ;; FIXME: URI forging is evil
        new-uri    (str "/store/"
                        store-v "/"
                        group "/"
                        artifact "/"
                        (t/thing->name artifact-v)
                        (:path-info request))
        new-req    (-> request
                       (assoc :uri new-uri)
                       (dissoc :context :path-info))]
    (if (privilaged-user-agents user-agent)
      (#'app new-req) ;; pass it forwards
      (wutil/moved-temporary new-uri))))

(defn upgrade-v0-munged->v1-req
  [request group artifact version platform ns symbol]
  (let [user-agent (get-in request [:headers "user-agent"])
        new-symbol (util/update-munge symbol)
        new-uri    (str "/store/v1/"
                        (-> (t/->Group group)
                            (t/->Artifact artifact)
                            (t/->Version version)
                            (t/->Platform platform)
                            (t/->Ns ns)
                            (t/->Def new-symbol)
                            (t/thing->url-path)))
        new-req    (-> request
                       (assoc :uri new-uri)
                       (dissoc :context :path-info))]
    (when-not (= (:uri request) new-uri)
      (if (privilaged-user-agents user-agent)
        (#'app new-req) ;; pass it forwards
        (wutil/moved-permanently new-uri)))))

(defn upgrade-v0->v1-req
  [request]
  (let [user-agent (get-in request [:headers "user-agent"])
        new-symbol (util/update-munge symbol)
        rest-path  (rest (string/split (:path-info request) #"/"))
        _          (println rest-path)
        rest-path  (if (> (count rest-path) 3)
                     (concat (take 3 rest-path) ["clj"] (drop 3 rest-path))
                     rest-path)
        new-uri    (str "/store/v1/" (string/join "/" rest-path))
        new-req    (-> request
                       (assoc :uri new-uri)
                       (dissoc :context :path-info))]
    (if (privilaged-user-agents user-agent)
      (#'app new-req) ;; pass it forwards
      (wutil/moved-permanently new-uri))))

(defroutes app-routes
  (GET "/" {uri :uri :as req}
    (do (log! req nil)
        (v.c.cs/cheatsheet)))

  (GET "/favicon.ico" []
    (response/redirect "/public/favicon.ico"))

  (GET "/robots.txt" []
    (response/redirect "/public/robots.txt"))

  (route/resources "/public")

  ;; The main browsing interface
  ;;--------------------------------------------------------------------
  ;; Redirect Grimoire 0.3 legacy paths into the store
  (context ["/:version",
            :version #"[0-9]+.[0-9]+.[0-9]+"]
      [version]
    #(upgrade-unstored-req %1 version))

  (GET "/store" []
    (wutil/moved-permanently (:store-url (cfg/site-config))))

  ;; Handle pre-versioned store (Grimoire 0.3) store links
  (context ["/store/:group", :group #"[^v][^0-9/]*"]
      [group]
    #(upgrade-unversioned-store-req %1 group))

  ;; Upgrade v0 and previous munging
  (context ["/store/v0/:group/:artifact/:version/:platform/:ns/:symbol"
            :platform platform-regex]
      [group artifact version platform ns symbol]
    #(upgrade-v0-munged->v1-req %1 group artifact version platform ns symbol))

  ;; Upgrade store version
  (context ["/store/v0"] []
    upgrade-v0->v1-req)

  ;; Handle "latest" links
  (context ["/store/:store-v/:group/:artifact/latest",
            :store-v #"v[0-9]+"]
      [store-v group artifact]
    #(rewrite-latest->version-req %1 store-v group artifact))

  ;; The store itself
  ;;--------------------------------------------------------------------
  store-v1
  
  ;; Symbol search interface
  ;;--------------------------------------------------------------------
  search

  ;; The v0 API
  ;;--------------------------------------------------------------------

  ;; The old v0 api
  api-v0

  ;; Upgrade v1 requests to v2 requests
  (context ["/api/v1/:group/:artifact/:version/:platform/:ns/:symbol"
            :platform platform-regex]
      [group artifact version platform ns symbol]
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            new-symbol (util/update-munge symbol)
            new-uri    (str "/api/v2/"
                            (-> (t/->Group group)
                                (t/->Artifact artifact)
                                (t/->Version version)
                                (t/->Platform platform)
                                (t/->Ns ns)
                                (t/->Def new-symbol)
                                (t/thing->url-path))
                            (:query-string request))
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (when-not (= (:uri request) new-uri)
          (if (privilaged-user-agents user-agent)
            (#'app new-req) ;; pass it forwards
            (wutil/moved-permanently new-uri)))))) ;; everyone else

  ;; The v2 api itself
  api-v2

  ;; Articles
  ;;--------------------------------------------------------------------
  (GET "/api" []
    (v/markdown-page "articles/API"))

  (GET "/contributing" []
    (v/markdown-page "articles/contributing"))

  (GET "/about" []
    (v/markdown-page "articles/about"))

  (GET "/heatmap" []
    (v.c.h/heatmap-page))

  (context ["/funding"] []
    (fn [request]
      (sdb/update! (cfg/simpledb-config)
                   [:funding :thought-about-it]
                   incf)
      (merge
       (assoc-in request [:session :thought-about-it] true)
       (response/redirect "https://www.patreon.com/arrdem"))))

  (GET "/worklist" []
    (v.c.h/worklist-page))

  (GET "/sitemap.xml" []
    (v/sitemap-page))

  (route/not-found
   (fn [{uri :uri :as req}]
     (warn (pr-str {:uri        uri
                    :type       :html
                    :user-agent (get-in req [:headers "user-agent"])}))
     (v.e/error-404))))

(defn update-cookie-counter [request]
  (-> request
      (update-in [:session :counter]          (fnil inc 0))
      (update-in [:session :thought-about-it] (fnil identity false))))

(defn app [request]
  (let [request (update-cookie-counter request)
        resp    (app-routes request)]
    (if-not (:session resp)
      (assoc resp :session (:session request))
      resp)))
