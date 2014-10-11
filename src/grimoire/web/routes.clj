(ns grimoire.web.routes
  (:require [compojure.core :refer [defroutes context GET let-routes]]
            [compojure.route :as route]
            [grimoire.web
             [util :as util]
             [views :as views]
             [api   :as api]]
            [ring.util.response :as response]
            [taoensso.timbre :as timbre :refer [info warn]]))


(defroutes store
    (context ["/store"] []
    (GET "/" {uri :uri}
      (info (pr-str {:uri uri :type :text}))
      (views/api-page))

    (context ["/:groupid"] [groupid]
      (GET "/" {uri :uri}
        (info (pr-str {:uri uri :type :text}))
        (views/groupid-page groupid))

      (context ["/:artifactid"] [artifactid]
        (GET "/" {uri :uri}
          (info (pr-str {:uri uri :type :text}))
          (views/artifactid-page groupid artifactid))

        (context ["/:version", :version #"[0-9]+.[0-9]+.[0-9]+"] [version]
          (GET "/" {uri :uri}
            (info (pr-str {:uri uri :type :text}))
            (views/version-page groupid artifactid version))

          (context "/:namespace" [namespace]
            (GET "/" {uri :uri}
              (info (pr-str {:uri uri :type :text}))
              (views/namespace-page-memo groupid artifactid version
                                         namespace))

            (context "/:symbol" [symbol]
              (GET "/" {header-type :content-type
                        {param-type :type} :params
                        :as req
                        uri :uri}
                (let [type    (or header-type param-type :html)
                      symbol' (util/unmunge symbol)]
                  (cond
                   ;; FIXME this is a bit of a hack to handle catch/finally
                   (#{"catch" "finally"} symbol)
                   ,,(response/redirect
                      (format "/%s/%s/%s/%s/%s/"
                              groupid artifactid version
                              namespace"try"))

                   ;; handle the case of redirecting due to munging
                   (not (= symbol symbol'))
                   ,,(response/redirect
                      (format "/%s/%s/%s/%s/%s/"
                              groupid artifactid version
                              namespace symbol'))

                   :else
                   ,,(let [res (views/symbol-page groupid artifactid version
                                                  namespace symbol type)]
                       (info (pr-str {:uri uri :type type}))
                       res))))

              (route/not-found
               (fn [{uri :uri}]
                 (warn (pr-str {:uri uri}))
                 (views/error-unknown-symbol groupid artifactid version
                                             namespace symbol))))

            (route/not-found
             (fn [{uri :uri}]
               (warn (pr-str {:uri uri}))
               (views/error-unknown-namespace groupid artifactid version
                                              namespace))))

          (route/not-found
           (fn [{uri :uri}]
             (warn (pr-str {:uri uri}))
             (views/error-unknown-version groupid artifactid version))))

        (route/not-found
         (fn [{uri :uri}]
           (warn (pr-str {:uri uri}))
           (views/error-unknown-artifact groupid artifactid))))

      (route/not-found
       (fn [{uri :uri}]
         (warn (pr-str {:uri uri}))
         (views/error-unknown-group groupid))))))

(defroutes api-v0
  (context ["/api/v0"] []
    (GET "/" {{op :op} :params}
      ;; op ∈ #{"groups"}
      )

    (context ["/:group"] [group]
      (GET "/" {{op :op} :params}
        ;; op ∈ #{"artifacts" "notes"}
        )

      (context ["/:artifact"] [artifact]
        (GET "/" {{op :op} :params}
          ;; op ∈ #{"versions" "notes" "url"}
          )

        (context ["/:version"] [version]
          (GET "/" {{op :op} :params}
            ;; op ∈ #{"namespaces" "notes"}
            )

          (context ["/:namespace"] [namespace]
            (GET "/" {{op :op} :params}
                ;; op ∈ #{"notes"
                ;;        "docs"
                ;;        "symbols"
                ;;        "vars"
                ;;        "fns"
                ;;        "types"
                ;;        "added"}
              )

            (context ["/:symbol"] [symbol]
              (GET "/" {{op :op} :params}
                ;; op ∈ #{"notes"
                ;;        "type"
                ;;        "added"
                ;;        "doc"
                ;;        "file"
                ;;        "line"
                ;;        "column"}
                ))))))))


(defroutes articles
  (context ["/articles"] []
    (GET "/:id" {{id :id} :params uri :uri}
      (when-let [res (views/markdown-page (str "articles/" id))]
        (info (pr-str {:uri uri :type :html}))
        res))

    (GET "/" {uri :uri}
      (info (pr-str {:uri uri :type :html}))
      (views/articles-list))

      (route/not-found
       (fn [{uri :uri}]
         (warn (pr-str {:uri uri}))
         (views/error-404)))))


(defroutes app
  (GET "/" {uri :uri}
    (info (pr-str {:uri uri :type :html}))
    (views/home-page))

  (GET "/favicon.ico" []
    (response/redirect "/public/favicon.ico"))

  (GET "/robots.txt" []
    (response/redirect "/public/robots.txt"))

  (route/resources "/public")

  ;; The main browsing interface
  store

  ;; The article store
  articles

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
     (views/error-404))))
