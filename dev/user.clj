(ns user
  (:require [grimoire.web :as web]))

(defn start! []
  (web-server/start-web-server!)
  :ok)

(defn stop! []
  (web-server/stop-web-server!)
  :ok)

(defn restart! []
  (stop!)
  (start!))

(def reset restart!)

(comment (restart!))
