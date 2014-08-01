(defn number?
  "Returns true if x is a Number"
  {:added "1.0"
   :static true}
  [x]
  (instance? Number x))