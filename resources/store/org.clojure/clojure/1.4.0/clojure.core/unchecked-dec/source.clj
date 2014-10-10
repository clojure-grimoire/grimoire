(defn unchecked-dec
  "Returns a number one less than x, a long.
  Note - uses a primitive operator subject to overflow."
  {:inline (fn [x] `(. clojure.lang.Numbers (unchecked_dec ~x)))
   :added "1.0"}
  [x] (. clojure.lang.Numbers (unchecked_dec x)))