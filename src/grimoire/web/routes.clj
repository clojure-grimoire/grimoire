(ns grimoire.web.routes
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [grimoire.web.util :as util]
            [grimoire.web.views :as views]
            [ring.util.response :as response]))

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

  (GET "/:version/" [version]
       (if (#{"1.4.0" "1.5.0" "1.6.0"} version)
         (views/version-page version)
         (views/error-unknown-version version)))

  (GET "/:version/:namespace/" [version namespace]
       (views/namespace-page-memo version namespace))

  (GET "/:version/:namespace/:symbol/"
       {header-type :content-type
        {param-type :type} :params
        [version namespace symbol] :params
        :as req}
       (if (#{"catch" "finally"} symbol)
         (response/redirect (str "/" version "/clojure.core/try/"))
         (views/symbol-page version namespace symbol
                            (keyword (or header-type param-type "text/html")))))

  (GET "/:version/:namespace/:symbol/docstring" [version namespace symbol]
       (util/resource-file-contents
        (str "resources/" version "/" namespace "/" symbol "/docstring.md")))

  (GET "/:version/:namespace/:symbol/extended-docstring" [version namespace symbol]
       (util/resource-file-contents
        (str "resources/" version "/" namespace "/" symbol "/extended-docstring.md")))

  (GET "/:version/:namespace/:symbol/related" [version namespace symbol]
       (util/resource-file-contents
        (str "resources/" version "/" namespace "/" symbol "/related.txt")))

  (GET "/:version/:namespace/:symbol/examples" [version namespace symbol]
       (views/all-examples version namespace symbol :text))

  (route/not-found (views/error-404)))
