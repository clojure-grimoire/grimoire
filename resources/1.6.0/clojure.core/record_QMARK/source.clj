(defn record?
  "Returns true if x is a record"
  {:added "1.6"
   :static true}
  [x]
  (instance? clojure.lang.IRecord x))