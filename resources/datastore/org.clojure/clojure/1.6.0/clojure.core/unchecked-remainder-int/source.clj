(defn unchecked-remainder-int
  "Returns the remainder of division of x by y, both int.
  Note - uses a primitive operator subject to truncation."
  {:inline (fn [x y] `(. clojure.lang.Numbers (unchecked_int_remainder ~x ~y)))
   :added "1.0"}
  [x y] (. clojure.lang.Numbers (unchecked_int_remainder x y)))