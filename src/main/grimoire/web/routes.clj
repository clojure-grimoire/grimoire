(ns grimoire.web.routes
  (:require [clojure.string :as string]
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
            [grimoire.web.views.errors :as v.e]
            [grimoire.web.views.api :as v.api]
            [ring.util.response :as response]
            [simpledb.core :as sdb]
            [taoensso.timbre :as timbre :refer [warn]]))

(def ^:private platform-regex
  #"clj|cljs|cljclr|pixi|kiss|ox|toc")

(def ^:private privilaged-user-agents
  #{"URL/Emacs"})

(def ^:private incf (fnil inc 0))

(defn- log! [request thing]
  ;; FIXME: could be one transaction
  
  ;; what do I want to know
  ;; - inc user-agent string tracking
  (let [db (cfg/simpledb-config)]
    (sdb/update! db
                 :clients update
                 (get-in request [:headers "user-agent"] "Unknown") incf)

    ;; - inc the thing URI in the "thing"
    (when thing
      ;; - if def, inc in the def store at ":artifact/:ns/:def"
      (when (t/def? thing)
        (sdb/update! db
                     :defs update
                     (t/thing->relative-path t/version thing) incf))

      (when-let [ns (t/thing->namespace thing)]
        (sdb/update! db
                     :namespaces update
                     (t/thing->name ns) incf))

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
                         (str (t/thing->name group) \/ (t/thing->name artifact)) incf)))))))

(defn store-v0
  [{header-type :content-type
    {param-type :type} :params
    :as req
    uri :uri}]
  (let [type    (-> (or header-type
                        param-type
                        :html)
                    wutil/normalize-type)
        log-msg (pr-str {:uri        uri
                         :type       type
                         :user-agent (get-in req [:headers "user-agent"])})]
    (->> (context "/store/v0" []
           (GET "/" {uri :uri}
             (when-let [r (v/store-page type)]
               (log! req nil)
               r))

           (context "/:groupid" [groupid]
             (let-routes [t (t/->Group groupid)]
               (GET "/" []
                 (when-let [r (v/group-page type t)]
                   (log! req t)
                   r))

               (context "/:artifactid" [artifactid]
                 (let-routes [t (t/->Artifact t artifactid)]
                   (GET "/" []
                     (when-let [r (v/artifact-page type t)]
                       (log! req t)
                       r))

                   (context "/:version" [version]
                     (let-routes [t (t/->Version t version)]
                       (GET "/" []
                         (when-let [r (v/version-page type t)]
                           (log! req t)
                           r))

                       (context ["/:platform"
                                 :platform platform-regex] [platform]
                         (let-routes [t (t/->Platform t platform)]
                           (GET "/" []
                             (when-let [r (v/platform-page type t)]
                               (log! req t)
                               r))

                           (context "/:namespace" [namespace]
                             (let-routes [t (t/->Ns t namespace)]
                               (GET "/" []
                                 (when-let [r (v/namespace-page-memo type t)]
                                   (log! req t)
                                   r))

                               (context "/:symbol" [symbol]
                                 (let-routes [t (t/->Def t symbol)]
                                   (GET "/" []
                                     (when-let [r (v/symbol-page type t)]
                                       (log! req t)
                                       r))

                                   (route/not-found
                                    (fn [req]
                                      (warn log-msg)
                                      (v.e/error-unknown-symbol type t)))))

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

(defmacro do-dispatch [dispatch type op t]
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

(def api-v1
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
           (context ["/api/v1"] []
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
                         (response/redirect new-uri))))))

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
                           (response/redirect new-uri))))))

                 (route/not-found
                  (fn [req]
                    (warn log-msg)
                    (v.e/search-no-symbol type "v1" ns))))

               (route/not-found
                (fn [req]
                  (warn log-msg)
                  (v.e/search-no-version type "v1"))))))

         (routing req))))

(defroutes app
  (GET "/" {uri :uri :as req}
    (do (log! req nil)
        (v.c.h/home-page)))

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
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            ;; FIXME: URI forging is evil

            path       (string/split (:uri request) #"/")
            path       (list* (first path) (second path) "clj" (drop 2 path))
            new-uri    (str "/store/v0/org.clojure/clojure"
                            (->> path (interpose "/") (apply str)))
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (if (privilaged-user-agents user-agent)
          (#'app new-req) ;; pass it forwards
          (wutil/moved-permanently new-uri))))) ;; everyone else

  (GET "/store" []
    (wutil/moved-permanently (:store-url (cfg/site-config))))
  
  ;; Handle pre-versioned store (Grimoire 0.4) store links
  (context ["/store/:t", :t #"[^v][^0-9]*"] [t]
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            ;; FIXME: URI forging is evil
            ;; FIXME: Forged URI doesn't have a platform part
            path       (string/split (:path-info request) #"/")
            path       (if (>= (count path) 3)
                         (concat (take 2 path) '("clj") (drop 2 path))
                         path)
            new-uri    (str "/store/v0/" t (apply str (interpose "/" path)))
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (if (privilaged-user-agents user-agent)
          (#'app new-req) ;; pass it forwards
          (wutil/moved-permanently new-uri))))) ;; everyone else

  ;; Handle "latest" links
  (context ["/store/:store-v/:group/:artifact/latest",
            :store-v #"v[0-9]+"]
      [store-v group artifact]
    (fn [request]
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
          (wutil/moved-permanently new-uri))))) ;; everyone else

  ;; Upgrade v0 and previous munging
  (context ["/store/v0/:group/:artifact/:version/:platform/:ns/:symbol"
            :platform platform-regex]
      [group artifact version platform ns symbol]
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            new-symbol (util/update-munge symbol)
            new-uri    (str "/store/v1/"
                            (-> (t/->Group group)
                                (t/->Artifact artifact)
                                (t/->Version version)
                                (t/->Platform platform)
                                (t/->Ns ns)
                                (t/->Def new-symbol)
                                (t/thing->url)))
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (when-not (= (:uri request) new-uri)
          (if (privilaged-user-agents user-agent)
            (#'app new-req) ;; pass it forwards
            (wutil/moved-permanently new-uri)))))) ;; everyone else

  ;; Upgrade store version
  (context ["/store/v0/"] []
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            new-symbol (util/update-munge symbol)
            new-uri    (str "/store/v1" (:path-info request))
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (if (privilaged-user-agents user-agent)
          (#'app new-req) ;; pass it forwards
          (wutil/moved-permanently new-uri)))))
  
  ;; The store itself
  store-v0

  ;; Symbol search interface
  ;;--------------------------------------------------------------------
  search

  ;; The v0 API
  ;;--------------------------------------------------------------------
  api-v0
  api-v1
  
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

  (GET "/sitemap.xml" []
    (v/sitemap-page))

  (route/not-found
   (fn [{uri :uri :as req}]
     (warn (pr-str {:uri        uri
                    :type       :html
                    :user-agent (get-in req [:headers "user-agent"])}))
     (v.e/error-404))))
