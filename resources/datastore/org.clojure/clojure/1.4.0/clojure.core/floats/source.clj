(definline floats
  "Casts to float[]"
  {:added "1.0"}
  [xs] `(. clojure.lang.Numbers floats ~xs))