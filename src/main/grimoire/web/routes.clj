(ns grimoire.web.routes
  (:require [compojure.core :refer [defroutes context GET let-routes routing]]
            [compojure.route :as route]
            [grimoire.util :as util]
            [grimoire.things :as thing]
            [grimoire.web.views :as v ]
            [grimoire.web.views.errors :as v.e]
            [grimoire.web.views.api :as v.api]
            [ring.util.response :as response]
            [taoensso.timbre :as timbre :refer [info warn]]))

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
                 (let-routes [t (thing/->T :artifact t artifactid)]
                   (GET "/" []
                     (when-let [r (v/artifact-page type t)]
                       (info log-msg)
                       r))

                   (context ["/:version"] [version]
                     (let-routes [t (thing/->T :version t version)]
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

                       (context "/:namespace" [namespace]
                         (let-routes [t (thing/->T :namespace t namespace)]
                           (GET "/" []
                             (cond
                               (= version "latest")
                               ,,(response/redirect
                                  (format "/store/%s/%s/%s/%s/"
                                          groupid
                                          artifactid
                                          (-> namespace v/ns-version-index :name)
                                          namespace))

                               :else
                               ,,(when-let [r (v/namespace-page-memo type t)]
                                   (info log-msg)
                                   r)))

                           (context "/:symbol" [symbol]
                             (let-routes [t (thing/->T :def t symbol)]
                               (GET "/" []
                                 (let [symbol' (util/update-munge symbol)]
                                   (cond
                                     (= version "latest")
                                     ,,(response/redirect
                                        (format "/store/%s/%s/%s/%s/%s/"
                                                groupid
                                                artifactid
                                                (-> namespace v/ns-version-index :name)
                                                namespace
                                                symbol))

                                     ;; handle the case of redirecting due to munging
                                     (not (= symbol symbol'))
                                     ,,(response/redirect
                                        (format "/store/%s/%s/%s/%s/%s/"
                                                groupid artifactid version
                                                namespace symbol'))

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
                         (let-routes [t (thing/->Ns t namespace)]
                           (GET "/" []
                             (do-dispatch v.api/namespace-ops
                                          type op t))

                           (context ["/:symbol"] [symbol]
                             (let-routes [t (thing/->Def t symbol)]
                               (GET "/" []
                                 (do-dispatch v.api/def-ops
                                              type op t))))))))))))))
       (routing req))))

(declare app)

(defroutes app
  (GET "/" {uri :uri :as req}
    (info (pr-str {:uri        uri
                   :type       :html
                   :user-agent (get-in req [:headers "user-agent"])}))
    (v/home-page))

  (GET "/favicon.ico" []
    (response/redirect "/public/favicon.ico"))

  (GET "/robots.txt" []
    (response/redirect "/public/robots.txt"))

  (route/resources "/public")

  ;; The main browsing interface
  store

  (GET "/api" []
    (v/markdown-page "articles/API"))

  (GET "/contributing" []
    (v/markdown-page "articles/contributing"))

  (GET "/about" []
    (v/markdown-page "articles/about"))

  ;; The v0 API
  api-v0

  ;; Redirect legacy paths into the store
  (context ["/:version", :version #"[0-9]+.[0-9]+.[0-9]+"] [version]
    (fn [request]
      (let [user-agent (get-in request [:headers "user-agent"])
            new-uri    (str "/store/org.clojure/clojure" (:uri request))
            new-req    (-> request
                          (assoc :uri new-uri)
                          (dissoc :context :path-info))]
        (println new-req)
        (if (= user-agent "URL/Emacs")
          (#'app new-req) ;; pass it forwards
          (response/redirect new-uri))))) ;; everyone else

  (route/not-found
   (fn [{uri :uri :as req}]
     (warn (pr-str {:uri        uri
                    :type       :html
                    :user-agent (get-in req [:headers "user-agent"])}))
     (v.e/error-404))))
