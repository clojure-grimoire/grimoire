(defn counted?
 "Returns true if coll implements count in constant time"
 {:added "1.0"
   :static true}
  [coll] (instance? clojure.lang.Counted coll))