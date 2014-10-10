(defn reversible?
 "Returns true if coll implements Reversible"
 {:added "1.0"
   :static true}
  [coll] (instance? clojure.lang.Reversible coll))