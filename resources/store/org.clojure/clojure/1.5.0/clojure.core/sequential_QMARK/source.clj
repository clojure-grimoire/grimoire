(defn sequential?
 "Returns true if coll implements Sequential"
 {:added "1.0"
  :static true}
  [coll] (instance? clojure.lang.Sequential coll))