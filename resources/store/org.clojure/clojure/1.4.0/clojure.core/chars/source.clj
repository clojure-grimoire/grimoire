(definline chars
  "Casts to chars[]"
  {:added "1.1"}
  [xs] `(. clojure.lang.Numbers chars ~xs))