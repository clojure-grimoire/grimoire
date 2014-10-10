(defn unchecked-negate-int
  "Returns the negation of x, an int.
  Note - uses a primitive operator subject to overflow."
  {:inline (fn [x] `(. clojure.lang.Numbers (unchecked_int_negate ~x)))
   :added "1.0"}
  [x] (. clojure.lang.Numbers (unchecked_int_negate x)))