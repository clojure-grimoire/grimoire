(defn min
  "Returns the least of the nums."
  {:added "1.0"
   :inline-arities >1?
   :inline (nary-inline 'min)}
  ([x] x)
  ([x y] (. clojure.lang.Numbers (min x y)))
  ([x y & more]
   (reduce1 min (min x y) more)))