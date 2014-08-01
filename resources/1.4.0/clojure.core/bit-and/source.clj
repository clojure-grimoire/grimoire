(defn bit-and
  "Bitwise and"
   {:inline (nary-inline 'and)
    :inline-arities >1?
    :added "1.0"}
   ([x y] (. clojure.lang.Numbers and x y))
   ([x y & more]
      (reduce1 bit-and (bit-and x y) more)))