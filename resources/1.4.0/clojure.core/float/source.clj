(defn float
  "Coerce to float"
  {:inline (fn  [x] `(. clojure.lang.RT (~(if *unchecked-math* 'uncheckedFloatCast 'floatCast) ~x)))
   :added "1.0"}
  [^Number x] (clojure.lang.RT/floatCast x))