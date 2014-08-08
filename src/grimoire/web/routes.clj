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

  (context "/:version/:namespace/:symbol/" [version namespace symbol]

           (GET "/" {header-type :content-type
                     {param-type :type} :params
                     :as req}
                (if (#{"catch" "finally"} symbol)
                  (response/redirect (str "/" version "/clojure.core/try/"))
                  (views/symbol-page version namespace symbol :html)))

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
                (views/all-examples version namespace symbol :text)))

  (route/not-found (views/error-404)))
