(defn unsigned-bit-shift-right
  "Bitwise shift right, without sign-extension."
  {:inline (fn [x n] `(. clojure.lang.Numbers (unsignedShiftRight ~x ~n)))
   :added "1.6"}
  [x n] (. clojure.lang.Numbers unsignedShiftRight x n))