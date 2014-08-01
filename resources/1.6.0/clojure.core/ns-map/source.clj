(defn ns-map
  "Returns a map of all the mappings for the namespace."
  {:added "1.0"
   :static true}
  [ns]
  (.getMappings (the-ns ns)))