(ns grimoire.web.routes
  (:require [compojure.core :refer [defroutes context GET let-routes routing]]
            [compojure.route :as route]
            [grimoire.util :as util]
            [grimoire.things :as thing]
            [grimoire.web.views :as v]
            [grimoire.web.views.content.html :as v.c.h]
            [grimoire.web.views.errors :as v.e]
            [grimoire.web.views.api :as v.api]
            [ring.util.response :as response]
            [taoensso.timbre :as timbre :refer [info warn]]))

(defn moved-permanently
  "Returns a Ring response for a HTTP 301 'moved permanently' redirect."
  {:added "1.3"}
  ;; FIXME: remove for ring-clojure/ring#181
  [url]
  {:status  301
   :headers {"Location" url}
   :body    ""})

(def normalize-type
  {:html              :text/html
   :text/html         :text/html
   "html"             :text/html
   "text/html"        :text/html

   :text              :text/plain
   :text/plain        :text/plain
   "text"             :text/plain
   "text/plain"       :text/plain

   "json"             :application/json
   :json              :application/json
   "application/json" :application/json
   :application/json  :application/json

   "edn"              :application/edn
   :edn               :application/edn
   "application/edn"  :application/edn
   :application/edn   :application/edn
   })

(def store
  (fn [{header-type :content-type
        {param-type :type} :params
        :as req
        uri :uri}]
    (->> (let-routes [type    (-> (or header-type
                                      param-type
                                      :html)
                                  normalize-type)
                      log-msg (pr-str {:uri        uri
                                       :type       type
                                       :user-agent (get-in req [:headers "user-agent"])})]
           (context ["/store"] []
             (GET "/" {uri :uri}
               (when-let [r (v/store-page type)]
                 (info log-msg)
                 r))

             (context ["/:groupid"] [groupid]
               (let-routes [t (thing/->Group groupid)]
                 (GET "/" []
                   (when-let [r (v/group-page type t)]
                     (info log-msg)
                     r))

                 (context ["/:artifactid"] [artifactid]
                   (let-routes [t (thing/->Artifact t artifactid)]
                     (GET "/" []
                       (when-let [r (v/artifact-page type t)]
                         (info log-msg)
                         r))

                     (context ["/:version"] [version]
                       (let-routes [t (thing/->Version t version)]
                         (GET "/" []
                           (cond
                             (= version "latest")
                             ,,(response/redirect
                                (format "/store/%s/%s/%s/"
                                        groupid
                                        artifactid
                                        (-> namespace v/ns-version-index :name)))

                             :else
                             ,,(when-let [r (v/version-page type t)]
                                 (info log-msg)
                                 r)))

                         (context ["/:platform", :platform #"clj|cljs|cljclr|pixi"] [platform]
                           (let-routes [t (thing/->Platform t platform)]
                             (GET "/" []
                               (cond
                                 (= version "latest")
                                 ,,(response/redirect
                                    (format "/store/%s/%s/%s/%s/%s/"
                                            groupid
                                            artifactid
                                            (-> namespace v/ns-version-index :name)
                                            platform))

                                 :else
                                 ,,(when-let [r (v/platform-page type t)]
                                     (info log-msg)
                                     r)))

                             (context "/:namespace" [namespace]
                               (let-routes [t (thing/->Ns t namespace)]
                                 (GET "/" []
                                   (cond
                                     (= version "latest")
                                     ,,(response/redirect
                                        (format "/store/%s/%s/%s/%s/%s/"
                                                groupid
                                                artifactid
                                                (-> namespace v/ns-version-index :name)
                                                platform
                                                namespace))

                                     :else
                                     ,,(when-let [r (v/namespace-page-memo type t)]
                                         (info log-msg)
                                         r)))

                                 (context "/:symbol" [symbol]
                                   (let-routes [t (thing/->Def t symbol)]
                                     (GET "/" []
                                       (let [symbol' (util/update-munge symbol)]
                                         (cond
                                           (= version "latest")
                                           ,,(response/redirect
                                              (format "/store/%s/%s/%s/%s/%s/%s/"
                                                      groupid
                                                      artifactid
                                                      (-> namespace v/ns-version-index :name)
                                                      platform
                                                      namespace
                                                      symbol))

                                           ;; handle the case of redirecting due to munging
                                           (not (= symbol symbol'))
                                           ,,(response/redirect
                                              (format "/store/%s/%s/%s/%s/%s/%s/"
                                                      groupid artifactid version
                                                      platform namespace symbol'))

                                           :else
                                           ,,(when-let [r (v/symbol-page type t)]
                                               (info log-msg)
                                               r))))

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
                    (v.e/error-unknown-group t)))))))

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
    (->> (let-routes [type    (normalize-type
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
    (->> (let-routes [type    (normalize-type
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

(def search
  (fn [{header-type :content-type
        {param-type :type} :params
        :as req
        uri :uri}]
    (->> (let-routes [type    (-> (or header-type
                                      param-type
                                      :html)
                                  normalize-type)
                      log-msg (pr-str {:uri        uri
                                       :type       type
                                       :user-agent (get-in req [:headers "user-agent"])})]
           (context "/search" []
             (context "/v0" []
               (context "/:ns" [ns]
                 (context "/:symbol" [symbol]
                   (fn [request]
                     (when-let [v-thing (-> ns v/ns-version-index)]
                       (let [user-agent (get-in request [:headers "user-agent"])
                             new-uri    (format "/store/%s/%s/%s/%s/%s/%s"
                                                (:name (thing/thing->group v-thing))
                                                (:name (thing/thing->artifact v-thing))
                                                (:name v-thing)
                                                ns
                                                symbol)
                             new-req    (-> request
                                            (assoc :uri new-uri)
                                            (dissoc :context :path-info))]
                         (info log-msg)
                         (if (= user-agent "URL/Emacs")
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
                               new-uri    (format "/store/%s/%s/%s/clj/%s/%s"
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
                           (if (= user-agent "URL/Emacs")
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

           (routing req)))))

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
  store

  ;; Symbol search interface
  search

  (GET "/api" []
    (v/markdown-page "articles/API"))

  (GET "/contributing" []
    (v/markdown-page "articles/contributing"))

  (GET "/about" []
    (v/markdown-page "articles/about"))

  ;; The v0 API
  api-v0
  api-v1

  ;; Redirect legacy paths into the store
  (context ["/:version", :version #"[0-9]+.[0-9]+.[0-9]+"] [version]
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            new-uri    (str "/store/org.clojure/clojure" (:uri request))
            new-req    (-> request
                           (assoc :uri new-uri)
                           (dissoc :context :path-info))]
        (if (= user-agent "URL/Emacs")
          (#'app new-req) ;; pass it forwards
          (moved-permanently new-uri))))) ;; everyone else

  (route/not-found
   (fn [{uri :uri :as req}]
     (warn (pr-str {:uri        uri
                    :type       :html
                    :user-agent (get-in req [:headers "user-agent"])}))
     (v.e/error-404))))
