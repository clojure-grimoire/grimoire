(definline longs
  "Casts to long[]"
  {:added "1.0"}
  [xs] `(. clojure.lang.Numbers longs ~xs))