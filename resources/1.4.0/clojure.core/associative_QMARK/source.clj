(defn associative?
 "Returns true if coll implements Associative"
 {:added "1.0"
  :static true}
  [coll] (instance? clojure.lang.Associative coll))