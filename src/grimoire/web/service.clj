(ns grimoire.web.service
  (:require [compojure.handler :as handler]
            [grimoire.web.routes :refer [app]]
            [ring.adapter.jetty :as jetty]))

(defonce service (atom nil))

(defn start-web-server! []
  (reset! service
          (jetty/run-jetty (handler/site app)
                           {:port 3000 :join? false})))

(defn stop-web-server! []
  (when-let [service* @service]
    (.stop service*)
    (reset! service nil)))

(defn restart-web-server! []
  (stop-web-server!)
  (start-web-server!))

(defn -main [& args]
  (start-web-server!))
