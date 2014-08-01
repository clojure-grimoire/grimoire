(defn *'
  "Returns the product of nums. (*) returns 1. Supports arbitrary precision.
  See also: *"
  {:inline (nary-inline 'multiplyP)
   :inline-arities >1?
   :added "1.0"}
  ([] 1)
  ([x] (cast Number x))
  ([x y] (. clojure.lang.Numbers (multiplyP x y)))
  ([x y & more]
   (reduce1 *' (*' x y) more)))