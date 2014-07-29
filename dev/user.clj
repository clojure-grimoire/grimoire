(ns user
  (:require [grimoire.web :as web]))

(defn start! []
  (web/start-web-server!)
  :ok)

(defn stop! []
  (web/stop-web-server!)
  :ok)

(defn restart! []
  (stop!)
  (start!))

(def reset restart!)

(comment (restart!))
