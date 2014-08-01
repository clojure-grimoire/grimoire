(defn bit-or
  "Bitwise or"
  {:inline (nary-inline 'or)
   :inline-arities >1?
   :added "1.0"}
  ([x y] (. clojure.lang.Numbers or x y))
  ([x y & more]
    (reduce1 bit-or (bit-or x y) more)))