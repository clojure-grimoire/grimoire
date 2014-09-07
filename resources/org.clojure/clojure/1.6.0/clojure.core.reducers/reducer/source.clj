(defn reducer
  "Given a reducible collection, and a transformation function xf,
  returns a reducible collection, where any supplied reducing
  fn will be transformed by xf. xf is a function of reducing fn to
  reducing fn."
  {:added "1.5"}
  ([coll xf]
     (reify
      clojure.core.protocols/CollReduce
      (coll-reduce [this f1]
                   (clojure.core.protocols/coll-reduce this f1 (f1)))
      (coll-reduce [_ f1 init]
                   (clojure.core.protocols/coll-reduce coll (xf f1) init)))))