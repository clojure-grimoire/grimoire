(defn hash-set
  "Returns a new hash set with supplied keys."
  {:added "1.0"
   :static true}
  ([] #{})
  ([& keys]
   (clojure.lang.PersistentHashSet/createWithCheck keys)))