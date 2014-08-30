(ns grimoire.web.routes
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [grimoire.web.util :as util]
            [grimoire.web.views :as views]
            [ring.util.response :as response]
            [detritus.logging :refer [info warn]]))

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

  (route/resources "/public")

  (context "/:version" [version]
           (GET "/" {uri :uri}
                (if (#{"1.4.0" "1.5.0" "1.6.0"} version)
                  (do (info (pr-str {:uri uri :type :html}))
                      (views/version-page version))

                  (do (warn (pr-str {:uri uri}))
                      (views/error-unknown-version version))))

           (context "/:namespace" [namespace]
                    (GET "/" {uri :uri}
                         (do (info (pr-str {:uri uri :type :html}))
                             (views/namespace-page-memo version namespace)))

                    (context "/:symbol" [symbol]
                             (GET "/" {header-type :content-type
                                       {param-type :type} :params
                                       :as req
                                       uri :uri}
                                  (let [type (or header-type param-type :html)]
                                    (if (#{"catch" "finally"} symbol)
                                      (response/redirect (str "/" version "/clojure.core/try/"))
                                      (do (info (pr-str {:uri uri :type :html}))
                                          (views/symbol-page version namespace symbol type)))))

                             (GET "/docstring" {uri :uri}
                                  (do (info (pr-str {:uri uri :type :text}))
                                      (slurp (str "resources/" version "/" namespace "/" symbol "/docstring.md"))))
                             
                             (GET "/extended-docstring" {uri :uri}
                                  (do (info (pr-str {:uri uri :type :text}))
                                      (slurp (str "resources/" version "/" namespace "/" symbol "/extended-docstring.md"))))
                             
                             (GET "/related" {uri :uri}
                                  (do (info (pr-str {:uri uri :type :text}))
                                      (slurp (str "resources/" version "/" namespace "/" symbol "/related.txt"))))
                             
                             (GET "/examples" {uri :uri}
                                  (do (info (pr-str {:uri uri :type :text}))
                                      (views/all-examples version namespace symbol :text))))))

  (route/not-found
   (fn [{uri :uri}]
     (warn (pr-str {:uri uri}))
     (views/error-404))))
