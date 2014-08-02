(ns grimoire.web.service
  (:require [compojure.handler :as handler]
            [grimoire.web.routes :refer [app]]
            [ring.adapter.jetty :as jetty]))

(defonce service (atom nil))

(defn start-web-server! [& [port?]]
  (reset! service
          (jetty/run-jetty (handler/site app)
                           {:port (or port? 3000)
                            :join? false})))

(defn stop-web-server! []
  (when-let [service* @service]
    (.stop service*)
    (reset! service nil)))

(defn restart-web-server! [& [port?]]
  (stop-web-server!)
  (start-web-server! port?))

(defn -main [& [port?]]
  (start-web-server!
   (if (string? port?)
     (Long. port?)
     3000)))
