(defn quot
  "quot[ient] of dividing numerator by denominator."
  {:added "1.0"
   :static true
   :inline (fn [x y] `(. clojure.lang.Numbers (quotient ~x ~y)))}
  [num div]
    (. clojure.lang.Numbers (quotient num div)))