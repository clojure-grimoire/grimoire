(defn -'
  "If no ys are supplied, returns the negation of x, else subtracts
  the ys from x and returns the result. Supports arbitrary precision.
  See also: -"
  {:inline (nary-inline 'minusP)
   :inline-arities >0?
   :added "1.0"}
  ([x] (. clojure.lang.Numbers (minusP x)))
  ([x y] (. clojure.lang.Numbers (minusP x y)))
  ([x y & more]
   (reduce1 -' (-' x y) more)))