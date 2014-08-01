(defn sorted-set-by
  "Returns a new sorted set with supplied keys, using the supplied comparator."
  {:added "1.1"
   :static true} 
  ([comparator & keys]
   (clojure.lang.PersistentTreeSet/create comparator keys)))