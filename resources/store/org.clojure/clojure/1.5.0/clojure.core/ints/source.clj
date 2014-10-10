(definline ints
  "Casts to int[]"
  {:added "1.0"}
  [xs] `(. clojure.lang.Numbers ints ~xs))