(definline booleans
  "Casts to boolean[]"
  {:added "1.1"}
  [xs] `(. clojure.lang.Numbers booleans ~xs))