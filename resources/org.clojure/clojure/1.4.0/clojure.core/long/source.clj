(defn long
  "Coerce to long"
  {:inline (fn  [x] `(. clojure.lang.RT (longCast ~x)))
   :added "1.0"}
  [^Number x] (clojure.lang.RT/longCast x))