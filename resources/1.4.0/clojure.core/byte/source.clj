(defn byte
  "Coerce to byte"
  {:inline (fn  [x] `(. clojure.lang.RT (~(if *unchecked-math* 'uncheckedByteCast 'byteCast) ~x)))
   :added "1.0"}
  [^Number x] (clojure.lang.RT/byteCast x))