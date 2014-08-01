(defn +'
  "Returns the sum of nums. (+) returns 0. Supports arbitrary precision.
  See also: +"
  {:inline (nary-inline 'addP)
   :inline-arities >1?
   :added "1.0"}
  ([] 0)
  ([x] (cast Number x))
  ([x y] (. clojure.lang.Numbers (addP x y)))
  ([x y & more]
   (reduce1 +' (+' x y) more)))