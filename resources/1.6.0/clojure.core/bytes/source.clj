(definline bytes
  "Casts to bytes[]"
  {:added "1.1"}
  [xs] `(. clojure.lang.Numbers bytes ~xs))