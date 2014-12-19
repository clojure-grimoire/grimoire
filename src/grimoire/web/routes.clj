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
  {:html        :text/html
   :text/html   :text/html
   "html"       :text/html
   "text/html"  :text/html

   :text        :text/plain
   :text/plain  :text/plain
   "text"       :text/plain
   "text/plain" :text/plain
   })

(def store
  (fn [{header-type :content-type
       {param-type :type} :params
       :as req
       uri :uri}]
    (->> (let-routes [type    (normalize-type (or header-type param-type :html))
                    log-msg (pr-str {:uri uri :type type})]
         (context ["/store"] []
           (GET "/" {uri :uri}
             (info (pr-str {:uri uri :type :text}))
             (v/store-page type))

           (context ["/:groupid"] [groupid]
             (let-routes [t (thing/->Group groupid)]
               (GET "/" []
                 (info log-msg)
                 (v/group-page type t))

               (context ["/:artifactid"] [artifactid]
                 (let-routes [t (thing/->T :artifact t artifactid)]
                   (GET "/" []
                     (info log-msg)
                     (v/artifact-page type t))

                   (context ["/:version", :version #"[0-9]+.[0-9]+.[0-9]+"] [version]
                     (let-routes [t (thing/->T :version t version)]
                       (GET "/" []
                         (info log-msg)
                         (v/version-page type t))

                       (context "/:namespace" [namespace]
                         (let-routes [t (thing/->T :namespace t namespace)]
                           (GET "/" []
                             (info log-msg)
                             (v/namespace-page-memo type t))

                           (context "/:symbol" [symbol]
                             (let-routes [t (thing/->T :def t symbol)]
                               (GET "/" []
                                 (let [symbol' (util/update-munge symbol)]
                                   (cond
                                     ;; FIXME this is a bit of a hack to
                                     ;; handle catch/finally. Should be
                                     ;; generalized to other symbols but how
                                     ;; to represent it?
                                     (#{"catch" "finally"} symbol)
                                     ,,(response/redirect
                                        (format "/%s/%s/%s/%s/%s/"
                                                groupid artifactid version
                                                namespace "try"))

                                     ;; handle the case of redirecting due to munging
                                     (not (= symbol symbol'))
                                     ,,(response/redirect
                                        (format "/store/%s/%s/%s/%s/%s/"
                                                groupid artifactid version
                                                namespace symbol'))

                                     :else
                                     ,,(let [res (v/symbol-page type t)]
                                         (info log-msg)
                                         res))))

                               (route/not-found
                                (fn [req]
                                  (warn log-msg)
                                  (v.e/error-unknown-symbol type t))))))

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
                    (v.e/error-unknown-artifact t))))

               (route/not-found
                (fn [req]
                  (warn log-msg)
                  (v.e/error-unknown-group t)))))))
      (routing req))))

;; FIXME: Implement rather than stub
(defmacro api-log []
  `(info (-> ~'t
           (select-keys [:uri])
           (assoc :op ~'op))))

(defroutes api-v0
  (context ["/api/v0"] []
    (GET "/" {{op :op} :params}
      ((get v.api/root-ops op
            v.api/unknown-op) nil))
    
    (context ["/:group"] [group]
      (let-routes [t (thing/->Group group)]
        (GET "/" {{op :op} :params}
          (api-log)
          ((get v.api/group-ops op
                v.api/unknown-op) t))

        (context ["/:artifact"] [artifact]
          (let-routes [t (thing/->Artifact t artifact)]
            (GET "/" {{op :op} :params}
              (api-log)
              ((get v.api/artifact-ops op
                    v.api/unknown-op) t))

            (context ["/:version"] [version]
              (let-routes [t (thing/->Version t version)]
                (GET "/" {{op :op} :params}
                  (api-log)
                  ((get v.api/version-ops op
                        v.api/unknown-op) t))

                (context ["/:namespace"] [namespace]
                  (let-routes [t (thing/->Ns t namespace)]
                    (GET "/" {{op :op} :params}
                      (api-log)
                      ((get v.api/namespace-ops op
                            v.api/unknown-op) t))
                    
                    (context ["/:symbol"] [symbol]
                      (let-routes [t (thing/->Def t symbol)]
                        (GET "/" {{op :op} :params}
                          (api-log)
                          ((get v.api/def-ops op
                                v.api/unknown-op) t))))))))))))))

(defroutes app
  (GET "/" {uri :uri}
    (info (pr-str {:uri uri :type :html}))
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
      (warn "Redirecting!")
      (response/redirect (str "/store/org.clojure/clojure"
                              (:uri request)))))

  (route/not-found
   (fn [{uri :uri}]
     (warn (pr-str {:uri uri}))
     (v.e/error-404))))
