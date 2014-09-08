(defn unchecked-multiply
  "Returns the product of x and y, both long.
  Note - uses a primitive operator subject to overflow."
  {:inline (fn [x y] `(. clojure.lang.Numbers (unchecked_multiply ~x ~y)))
   :added "1.0"}
  [x y] (. clojure.lang.Numbers (unchecked_multiply x y)))