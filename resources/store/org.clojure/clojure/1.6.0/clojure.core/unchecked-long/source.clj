(defn unchecked-long
  "Coerce to long. Subject to rounding or truncation."
  {:inline (fn  [x] `(. clojure.lang.RT (uncheckedLongCast ~x)))
   :added "1.3"}
  [^Number x] (clojure.lang.RT/uncheckedLongCast x))