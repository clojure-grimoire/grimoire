(defn unchecked-add-int
  "Returns the sum of x and y, both int.
  Note - uses a primitive operator subject to overflow."
  {:inline (fn [x y] `(. clojure.lang.Numbers (unchecked_int_add ~x ~y)))
   :added "1.0"}
  [x y] (. clojure.lang.Numbers (unchecked_int_add x y)))