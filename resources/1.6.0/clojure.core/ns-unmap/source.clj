(defn ns-unmap
  "Removes the mappings for the symbol from the namespace."
  {:added "1.0"
   :static true}
  [ns sym]
  (.unmap (the-ns ns) sym))