(defn int
  "Coerce to int"
  {
   :inline (fn  [x] `(. clojure.lang.RT (~(if *unchecked-math* 'uncheckedIntCast 'intCast) ~x)))
   :added "1.0"}
  [x] (. clojure.lang.RT (intCast x)))