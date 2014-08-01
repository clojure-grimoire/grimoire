(defn bit-shift-left
  "Bitwise shift left"
  {:inline (fn [x n] `(. clojure.lang.Numbers (shiftLeft ~x ~n)))
   :added "1.0"}
  [x n] (. clojure.lang.Numbers shiftLeft x n))