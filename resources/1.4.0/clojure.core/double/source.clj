(defn double
  "Coerce to double"
  {:inline (fn  [x] `(. clojure.lang.RT (doubleCast ~x)))
   :added "1.0"}
  [^Number x] (clojure.lang.RT/doubleCast x))