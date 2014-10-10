(defn unchecked-double
  "Coerce to double. Subject to rounding."
  {:inline (fn  [x] `(. clojure.lang.RT (uncheckedDoubleCast ~x)))
   :added "1.3"}
  [^Number x] (clojure.lang.RT/uncheckedDoubleCast x))