(defn unchecked-int
  "Coerce to int. Subject to rounding or truncation."
  {:inline (fn  [x] `(. clojure.lang.RT (uncheckedIntCast ~x)))
   :added "1.3"}
  [^Number x] (clojure.lang.RT/uncheckedIntCast x))