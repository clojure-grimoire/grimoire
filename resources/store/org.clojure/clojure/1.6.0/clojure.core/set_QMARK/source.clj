(defn set?
  "Returns true if x implements IPersistentSet"
  {:added "1.0"
   :static true}
  [x] (instance? clojure.lang.IPersistentSet x))