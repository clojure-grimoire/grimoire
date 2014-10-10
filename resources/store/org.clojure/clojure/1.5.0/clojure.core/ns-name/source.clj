(defn ns-name
  "Returns the name of the namespace, a symbol."
  {:added "1.0"
   :static true}
  [ns]
  (.getName (the-ns ns)))