(defn unchecked-char
  "Coerce to char. Subject to rounding or truncation."
  {:inline (fn  [x] `(. clojure.lang.RT (uncheckedCharCast ~x)))
   :added "1.3"}
  [x] (. clojure.lang.RT (uncheckedCharCast x)))