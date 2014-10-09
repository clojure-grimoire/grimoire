(ns grimoire.web.routes
  (:require [compojure.core :refer [defroutes context GET let-routes]]
            [compojure.route :as route]
            [grimoire.web.util :as util]
            [grimoire.web.views :as views]
            [ring.util.response :as response]
            [taoensso.timbre :as timbre :refer [info warn]]))

(defroutes app
  (GET "/" {uri :uri}
    (info (pr-str {:uri uri :type :html}))
    (views/home-page))

  (GET "/about" {uri :uri}
    (info (pr-str {:uri uri :type :html}))
    (views/markdown-page "about"))

  (GET "/contributing" {uri :uri}
    (info (pr-str {:uri uri :type :html}))
    (views/markdown-page "contributing"))

  (GET "/api" {uri :uri}
    (info (pr-str {:uri uri :type :html}))
    (views/markdown-page "API"))

  (GET "/favicon.ico" []
    (response/redirect "/public/favicon.ico"))

  (GET "/robots.txt" []
    (response/redirect "/public/robots.txt"))

  (route/resources "/public")

  (context ["/:version", :version #"[0-9]+.[0-9]+.[0-9]+"] [version]
    (fn [request]
      (warn "Redirecting!")
      (response/redirect (str "/org.clojure/clojure" (:uri request)))))

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
          (when (#{"1.4.0" "1.5.0" "1.6.0"} version)
            (views/version-page groupid artifactid version)))

        (context "/:namespace" [namespace]
          (GET "/" {uri :uri}
            (views/namespace-page-memo groupid artifactid version
                                       namespace))

          (context "/:symbol" [symbol]
            (GET "/docstring" {uri :uri}
              (let [f  (util/resource-file groupid artifactid version
                                           namespace symbol "docstring.md")]
                (when (and f (.isFile f))
                  (info (pr-str {:uri uri :type :text}))
                  (slurp f))))

            (GET "/extended-docstring" {uri :uri}
              (let [f (util/resource-file groupid artifactid version
                                          namespace symbol "extended-docstring.md")]
                (when (and f (.isFile f))
                  (info (pr-str {:uri uri :type :text}))
                  (slurp f))))

            (GET "/related" {uri :uri}
              (let [f (util/resource-file groupid artifactid version
                                          namespace symbol "related.txt")]
                (when (and f (.isFile f))
                  (info (pr-str {:uri uri :type :text}))
                  (slurp f))))

            (GET "/examples" {uri :uri}
              (when-let [examples (views/all-examples groupid artifactid version
                                                      namespace symbol :text)]
                (info (pr-str {:uri uri :type :text}))
                examples))

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
       (views/error-unknown-group groupid))))

  (route/not-found
   (fn [{uri :uri}]
     (warn (pr-str {:uri uri}))
     (views/error-404))))
