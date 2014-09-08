(defn boolean
  "Coerce to boolean"
  {
   :inline (fn  [x] `(. clojure.lang.RT (booleanCast ~x)))
   :added "1.0"}
  [x] (clojure.lang.RT/booleanCast x))