(ns grimoire.site
  (:require [compojure.core :refer :all]
            [grimoire.site.api :as api]
            [grimoire.site.release :as release]
            [ring.adapter.jetty :as ring]
            [compojure.route :as route]
            [compojure.handler :as handler])
  (:gen-class))


(defroutes routes
  api/routes
  release/routes
  (route/resources "/")
  ;(route/not-found (layout/four-oh-four))
  )

(def application
  (handler/site routes))

(defn start [port]
  (ring/run-jetty application
   {:port port :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
