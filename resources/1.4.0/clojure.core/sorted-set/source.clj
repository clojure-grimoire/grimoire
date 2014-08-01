(defn sorted-set
  "Returns a new sorted set with supplied keys."
  {:added "1.0"
   :static true}
  ([& keys]
   (clojure.lang.PersistentTreeSet/create keys)))