(ns grimoire.web.routes
  (:require [compojure.core :refer [defroutes context GET]]
            [compojure.route :as route]
            [grimoire.web.util :as util]
            [grimoire.web.views :as views]
            [ring.util.response :as response]))

(defroutes app
  (GET "/" [] (views/home-page))
  (GET "/about" [] (views/markdown-page "about"))
  (GET "/contributing" [] (views/markdown-page "contributing"))
  (GET "/api" [] (views/markdown-page "API"))

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
             (if (#{"catch" "finally"} symbol)
               (response/redirect (str "/" version "/clojure.core/try"))
               (views/symbol-page version namespace symbol
                                  (keyword (or header-type param-type "html")))))

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
           (->> (let [path (str namespace "/" symbol "/examples/")]
                  (for [v (views/clojure-example-versions version)]
                    (let [examples (util/dir-list-as-strings (str "resources/" v "/" path))]
                      (str "Examples from Clojure " v "\n"
                           "--------------------------------------------------------------------------------\n"
                           (->> examples
                                (map-indexed views/raw-example)
                                (interpose "\n\n")
                                (apply str))))))
                (apply str))))))

  (route/not-found (views/error-404)))

