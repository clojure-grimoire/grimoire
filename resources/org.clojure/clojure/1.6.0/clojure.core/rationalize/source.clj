(defn rationalize
  "returns the rational value of num"
  {:added "1.0"
   :static true}
  [num]
  (. clojure.lang.Numbers (rationalize num)))