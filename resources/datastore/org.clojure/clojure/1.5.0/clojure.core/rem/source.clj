(defn rem
  "remainder of dividing numerator by denominator."
  {:added "1.0"
   :static true
   :inline (fn [x y] `(. clojure.lang.Numbers (remainder ~x ~y)))}
  [num div]
    (. clojure.lang.Numbers (remainder num div)))