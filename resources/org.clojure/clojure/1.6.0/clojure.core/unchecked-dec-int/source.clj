(defn unchecked-dec-int
  "Returns a number one less than x, an int.
  Note - uses a primitive operator subject to overflow."
  {:inline (fn [x] `(. clojure.lang.Numbers (unchecked_int_dec ~x)))
   :added "1.0"}
  [x] (. clojure.lang.Numbers (unchecked_int_dec x)))