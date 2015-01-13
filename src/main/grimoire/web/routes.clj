(ns grimoire.web.routes
  (:require [clojure.string :as string]
            [compojure.core :refer [defroutes context GET let-routes routing]]
            [compojure.route :as route]
            [grimoire.util :as util]
            [grimoire.api :as api]
            [grimoire.web.util :as wutil]
            [grimoire.things :as thing]
            [grimoire.either :as either]
            [grimoire.web.views :as v]
            [grimoire.web.views.content.html :as v.c.h]
            [grimoire.web.views.errors :as v.e]
            [grimoire.web.views.api :as v.api]
            [ring.util.response :as response]
            [taoensso.timbre :as timbre :refer [info warn]]))

(def ^:private platform-regex
  #"clj|cljs|cljclr|pixi|kiss|ox|toc")

(def ^:private privilaged-urls
  #{"URL/Emacs"})

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
               (info log-msg)
               r))

           (context "/:groupid" [groupid]
             (let-routes [t (thing/->Group groupid)]
               (GET "/" []
                 (when-let [r (v/group-page type t)]
                   (info log-msg)
                   r))

               (context "/:artifactid" [artifactid]
                 (let-routes [t (thing/->Artifact t artifactid)]
                   (GET "/" []
                     (when-let [r (v/artifact-page type t)]
                       (info log-msg)
                       r))

                   (context "/:version" [version]
                     (let-routes [t (thing/->Version t version)]
                       (GET "/" []
                         (when-let [r (v/version-page type t)]
                           (info log-msg)
                           r))

                       (context ["/:platform"
                                 :platform platform-regex] [platform]
                         (let-routes [t (thing/->Platform t platform)]
                           (GET "/" []
                             (when-let [r (v/platform-page type t)]
                               (info log-msg)
                               r))

                           (context "/:namespace" [namespace]
                             (let-routes [t (thing/->Ns t namespace)]
                               (GET "/" []
                                 (when-let [r (v/namespace-page-memo type t)]
                                   (info log-msg)
                                   r))

                               (context "/:symbol" [symbol]
                                 (let-routes [t (thing/->Def t symbol)]
                                   (GET "/" []
                                     (when-let [r (v/symbol-page type t)]
                                       (info log-msg)
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
  `(info (-> ~'t
             (select-keys [:uri])
             (assoc :op ~'op))))

(defmacro do-dispatch [dispatch type op t]
  `(or (when-let [f# (~dispatch ~op)]
         (when-let [r# (f# ~type ~t)]
           (info ~'log-msg)
           r#))
       (do (warn ~'log-msg)
           (v.api/unknown-op ~type ~op ~t))))

(def api-v0
  (fn [{header-type :content-type
        {param-type :type
         op         :op :as params} :params
        :as req
        uri :uri}]
    (->> (let-routes [type    (wutil/normalize-type
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
               (let-routes [t (thing/->Group group)]
                 (GET "/" []
                   (do-dispatch v.api/group-ops
                                type op t))

                 (context ["/:artifact"] [artifact]
                   (let-routes [t (thing/->Artifact t artifact)]
                     (GET "/" []
                       (do-dispatch v.api/artifact-ops
                                    type op t))

                     (context ["/:version"] [version]
                       (let-routes [t (thing/->Version t version)]
                         (GET "/" []
                           (do-dispatch v.api/version-ops
                                        type op t))

                         (context ["/:namespace"] [namespace]
                           (let-routes [t (-> t
                                              (thing/->Platform "clj")
                                              (thing/->Ns namespace))]
                             (GET "/" []
                               (do-dispatch v.api/namespace-ops
                                            type op t))

                             (context ["/:symbol"] [symbol]
                               (let-routes [t (thing/->Def t symbol)]
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
    (->> (let-routes [type    (wutil/normalize-type
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
               (let-routes [t (thing/->Group group)]
                 (GET "/" []
                   (do-dispatch v.api/group-ops
                                type op t))

                 (context ["/:artifact"] [artifact]
                   (let-routes [t (thing/->Artifact t artifact)]
                     (GET "/" []
                       (do-dispatch v.api/artifact-ops
                                    type op t))

                     (context ["/:version"] [version]
                       (let-routes [t (thing/->Version t version)]
                         (GET "/" []
                           (do-dispatch v.api/version-ops
                                        type op t))

                         (context ["/:platform"] [platform]
                           (let-routes [t (thing/->Platform t platform)]

                             (GET "/" []
                               (do-dispatch v.api/platform-ops
                                            type op t))

                             (context ["/:namespace"] [namespace]
                               (let-routes [t (thing/->Ns t namespace)]
                                 (GET "/" []
                                   (do-dispatch v.api/namespace-ops
                                                type op t))

                                 (context ["/:symbol"] [symbol]
                                   (let-routes [t (thing/->Def t symbol)]
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
                   (when-let [v-thing (-> ns v/ns-version-index)]
                     (let [user-agent (get-in request [:headers "user-agent"])
                           new-uri    (format "/store/v0/%s/%s/%s/%s/%s/%s"
                                              (:name (thing/thing->group v-thing))
                                              (:name (thing/thing->artifact v-thing))
                                              (:name v-thing)
                                              "clj"
                                              ns
                                              symbol)
                           new-req    (-> request
                                          (assoc :uri new-uri)
                                          (dissoc :context :path-info))]
                       (info log-msg)
                       (if (privilaged-urls user-agent)
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
                     (when-let [v-thing (-> ns v/ns-version-index)]
                       (let [user-agent (get-in request [:headers "user-agent"])
                             new-uri    (format "/store/v0/%s/%s/%s/%s/%s/%s"
                                                (:name (thing/thing->group v-thing))
                                                (:name (thing/thing->artifact v-thing))
                                                (:name v-thing)
                                                platform
                                                ns
                                                symbol)
                             new-req    (-> request
                                            (assoc :uri new-uri)
                                            (dissoc :context :path-info))]
                         (info log-msg)
                         (if (privilaged-urls user-agent)
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
    (info (pr-str {:uri        uri
                   :type       :html
                   :user-agent (get-in req [:headers "user-agent"])}))
    (v.c.h/home-page))

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
            ;; FIXME: Forged URI doesn't have a platform part
            path       (string/split (:uri request) #"/")
            path       (list* (first path) (second path) "clj" (drop 2 path))
            new-uri    (str "/store/v0/org.clojure/clojure"
                            (->> path (interpose "/") (apply str)))
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (if (privilaged-urls user-agent)
          (#'app new-req) ;; pass it forwards
          (wutil/moved-permanently new-uri))))) ;; everyone else

  (GET "/store" []
    (wutil/moved-permanently "/store/v0"))
  
  ;; Handle pre-versioned store (Grimoire 0.4) store links
  (context ["/store/:t", :t #"[^v][^0-9]*"] [t]
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            ;; FIXME: URI forging is evil
            ;; FIXME: Forged URI doesn't have a platform part
            path       (string/split (:path-info request) #"/")
            _          (println path)
            path       (if (>= (count path) 3)
                         (concat (take 2 path) '("clj") (drop 2 path))
                         path)
            new-uri    (str "/store/v0/" t (apply str (interpose "/" path)))
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (if (privilaged-urls user-agent)
          (#'app new-req) ;; pass it forwards
          (wutil/moved-permanently new-uri))))) ;; everyone else

  ;; Handle "latest" links
  (context ["/store/:store-v/:group/:artifact/latest",
            :store-v #"v[0-9]+"]
      [store-v group artifact]
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            t          (-> (thing/->Group group)
                           (thing/->Artifact artifact))
            versions   (-> v/site-config
                           (api/list-versions t)
                           either/result)
            artifact-v (first versions)
            uri        (:uri request)
            ;; FIXME: URI forging is evil
            new-uri    (str "/store/"
                            store-v "/"
                            group "/"
                            artifact "/"
                            (:name artifact-v)
                            (:path-info request))
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (if (privilaged-urls user-agent)
          (#'app new-req) ;; pass it forwards
          (wutil/moved-permanently new-uri))))) ;; everyone else

  ;; Upgrade munging
  (context ["/store/:store-v/:group/:artifact/:version/:platform/:ns/:symbol"
            :store-v  #"v[0-9]+"
            :platform platform-regex]
      [store-v group artifact version platform ns symbol]
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            symbol'    (util/update-munge symbol)
            new-uri    (str \/ "store"
                            \/ store-v
                            \/ group
                            \/ artifact
                            \/ version
                            \/ platform
                            \/ ns
                            \/ symbol')
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (when-not (= symbol symbol')
          (if (privilaged-urls user-agent)
            (#'app new-req) ;; pass it forwards
            (wutil/moved-permanently new-uri)))))) ;; everyone else

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

  (route/not-found
   (fn [{uri :uri :as req}]
     (warn (pr-str {:uri        uri
                    :type       :html
                    :user-agent (get-in req [:headers "user-agent"])}))
     (v.e/error-404))))
