(defn error-mode
  "Returns the error-mode of agent a.  See set-error-mode!"
  {:added "1.2"
   :static true}
  [^clojure.lang.Agent a]
  (.getErrorMode a))