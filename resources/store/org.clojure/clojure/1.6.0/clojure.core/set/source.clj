(defn set
  "Returns a set of the distinct elements of coll."
  {:added "1.0"
   :static true}
  [coll] (clojure.lang.PersistentHashSet/create (seq coll)))