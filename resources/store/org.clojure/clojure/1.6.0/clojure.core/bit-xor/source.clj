(defn bit-xor
  "Bitwise exclusive or"
  {:inline (nary-inline 'xor)
   :inline-arities >1?
   :added "1.0"}
  ([x y] (. clojure.lang.Numbers xor x y))
  ([x y & more]
    (reduce1 bit-xor (bit-xor x y) more)))