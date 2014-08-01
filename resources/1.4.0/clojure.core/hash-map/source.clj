(defn hash-map
  "keyval => key val
  Returns a new hash map with supplied mappings."
  {:added "1.0"
   :static true}
  ([] {})
  ([& keyvals]
   (. clojure.lang.PersistentHashMap (createWithCheck keyvals))))