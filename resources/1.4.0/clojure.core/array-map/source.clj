(defn array-map
  "Constructs an array-map."
  {:added "1.0"
   :static true}
  ([] (. clojure.lang.PersistentArrayMap EMPTY))
  ([& keyvals] (clojure.lang.PersistentArrayMap/createWithCheck (to-array keyvals))))