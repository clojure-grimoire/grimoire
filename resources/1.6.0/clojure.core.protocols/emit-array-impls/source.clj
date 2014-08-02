(defmacro emit-array-impls
  [& syms]
  `(extend-protocol InternalReduce
     ~@(emit-array-impls* syms)))