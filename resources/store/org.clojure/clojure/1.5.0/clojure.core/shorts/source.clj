(definline shorts
  "Casts to shorts[]"
  {:added "1.1"}
  [xs] `(. clojure.lang.Numbers shorts ~xs))