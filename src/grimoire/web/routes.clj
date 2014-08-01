(ns grimoire.web.routes
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [grimoire.web.util :as util]
            [grimoire.web.views :as views]
            [ring.util.response :as response]
            [clojure.pprint :as pp]))

(defroutes app
  (GET "/" []
       (views/home-page))

  (GET "/about" []
       (views/markdown-page "about"))

  (GET "/contributing" []
       (views/markdown-page "contributing"))

  (GET "/api" []
       (views/markdown-page "API"))

  (route/resources "/public")

  (context "/:version" [version]
    (GET "/" [version]
         (when (#{"1.4.0" "1.5.0" "1.6.0"} version)
           (views/version-page version)))

    (context "/:namespace" [namespace]
      (GET "/" [version namespace]
           (views/namespace-page-memo version namespace))

      (context "/:symbol" [symbol]
        (GET "/" {header-type :content-type
                  {param-type :type} :params
                  :as req}
             (println header-type param-type)
             (pp/pprint req)
             (if (#{"catch" "finally"} symbol)
               (response/redirect (str "/" version "/clojure.core/try"))
               (views/symbol-page version namespace symbol
                                  (keyword (or header-type param-type "text/html")))))

      (GET "/docstring" []
           (util/resource-file-contents
            (str "resources/" version "/" namespace "/" symbol "/docstring.md")))

      (GET "/extended-docstring" []
           (util/resource-file-contents
            (str "resources/" version "/" namespace "/" symbol "/extended-docstring.md")))

      (GET "/related" []
           (util/resource-file-contents
            (str "resources/" version "/" namespace "/" symbol "/related.txt")))

      (GET "/examples" []
           (views/all-examples version namespace symbol :text)))))

  (route/not-found (views/error-404)))

