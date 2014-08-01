(defmacro refer-clojure
  "Same as (refer 'clojure.core <filters>)"
  {:added "1.0"}
  [& filters]
  `(clojure.core/refer '~'clojure.core ~@filters))