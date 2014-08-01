(ns grimoire.web.routes
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [grimoire.web.views :as views]))

(defroutes app
  (GET "/" [] (views/home-page))
  (GET "/about" [] (views/markdown-page "about"))
  (GET "/contributing" [] (views/markdown-page "contributing"))

  (route/resources "/public")

  (context "/:version" [version]
    (GET "/" [version]
         (when (#{"1.4.0" "1.5.0" "1.6.0"} version)
           (views/version-page version)))

    (context "/:namespace" [namespace]
      (GET "/" [version namespace]
           (views/namespace-page-memo version namespace))

      (context "/:symbol" [symbol]
        (GET "/" {{header-type :type} :headers
                  {param-type :type} :params}
             (views/symbol-page version namespace symbol
                          (keyword (or header-type param-type "html")))))))

  (route/not-found (views/error-404)))

