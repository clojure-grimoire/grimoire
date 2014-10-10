(defn float?
  "Returns true if n is a floating point number"
  {:added "1.0"
   :static true}
  [n]
  (or (instance? Double n)
      (instance? Float n)))