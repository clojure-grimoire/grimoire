(ns grimoire.web.service
  (:require [compojure.handler :as handler]
            [ring.middleware.session :as session]
            [grimoire.web.routes :refer [app]]
            [grimoire.web.config :as cfg]
            [ring.adapter.jetty :as jetty]
            [simpledb.core :as sdb]))

(defn start-web-server! [& [port? file?]]
  (println "starting!")
  (let [jetty-cfg {:port (or port? 3000)
                   :host "127.0.0.1"
                   :join? false}
        jetty     (-> app
                      handler/site
                      session/wrap-session
                      (jetty/run-jetty jetty-cfg))]

    ;; boot the webapp itself
    (reset! cfg/service
            (cfg/->Instance jetty
                            (sdb/init! {:file     "/srv/www/grimoire/analytics.db"
                                        :interval [:minutes 5]})
                            cfg/lib-grim-config
                            cfg/site-config))

    ;; Return nil b/c side-effects
    nil))

(defn stop-web-server! []
  (when-let [service* @cfg/service]
    (.stop (:jetty service*))
    (sdb/flush! (:simpledb service*))
    (sdb/stop!  (:simpledb service*))
    (reset! cfg/service nil)

    ;; Return nil b/c side-effects
    nil))

(defn restart-web-server! [& [port?]]
  (require 'grimoire.web.routes
           'grimoire.web.service
           :reload)
  (stop-web-server!)
  (start-web-server! port?)

  ;; Return nil b/c side-effects
  nil)

(defn -main [& [port?]]
  ;; Boot the webserver
  (start-web-server!
   (if (string? port?)
     (Long. port?)
     3000))

  ;; Return nil b/c side-effects
  nil)
