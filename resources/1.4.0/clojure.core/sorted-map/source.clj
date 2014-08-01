(defn sorted-map
  "keyval => key val
  Returns a new sorted map with supplied mappings."
  {:added "1.0"
   :static true}
  ([& keyvals]
   (clojure.lang.PersistentTreeMap/create keyvals)))