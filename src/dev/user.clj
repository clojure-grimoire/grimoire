(ns user
  (:require [grimoire.web.service :as web]
            [grimoire.api :as api]
            [grimoire.either :refer [succeed? result]]
            [grimoire.web.config :as cfg]
            [grimoire.web.views :refer [ns-version-index]]
            [grimoire.things :as t]))

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
