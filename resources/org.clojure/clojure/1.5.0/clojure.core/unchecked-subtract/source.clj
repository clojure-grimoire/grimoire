(defn unchecked-subtract
  "Returns the difference of x and y, both long.
  Note - uses a primitive operator subject to overflow."
  {:inline (fn [x y] `(. clojure.lang.Numbers (unchecked_minus ~x ~y)))
   :added "1.0"}
  [x y] (. clojure.lang.Numbers (unchecked_minus x y)))