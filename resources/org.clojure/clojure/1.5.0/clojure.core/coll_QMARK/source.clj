(defn coll?
  "Returns true if x implements IPersistentCollection"
  {:added "1.0"
   :static true}
  [x] (instance? clojure.lang.IPersistentCollection x))