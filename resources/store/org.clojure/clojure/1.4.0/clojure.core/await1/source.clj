(defn ^:static await1 [^clojure.lang.Agent a]
  (when (pos? (.getQueueCount a))
    (await a))
    a)