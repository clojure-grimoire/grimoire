(defn sorted?
 "Returns true if coll implements Sorted"
 {:added "1.0"
   :static true}
  [coll] (instance? clojure.lang.Sorted coll))