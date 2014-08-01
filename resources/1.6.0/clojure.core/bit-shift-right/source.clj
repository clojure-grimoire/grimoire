(defn bit-shift-right
  "Bitwise shift right"
  {:inline (fn [x n] `(. clojure.lang.Numbers (shiftRight ~x ~n)))
   :added "1.0"}
  [x n] (. clojure.lang.Numbers shiftRight x n))