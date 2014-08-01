(defn bit-and-not
  "Bitwise and with complement"
  {:inline (nary-inline 'andNot)
   :inline-arities >1?
   :added "1.0"
   :static true}
  ([x y] (. clojure.lang.Numbers andNot x y))
  ([x y & more]
    (reduce1 bit-and-not (bit-and-not x y) more)))