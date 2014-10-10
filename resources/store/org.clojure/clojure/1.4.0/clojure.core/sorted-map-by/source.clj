(defn sorted-map-by
  "keyval => key val
  Returns a new sorted map with supplied mappings, using the supplied comparator."
  {:added "1.0"
   :static true}
  ([comparator & keyvals]
   (clojure.lang.PersistentTreeMap/create comparator keyvals)))