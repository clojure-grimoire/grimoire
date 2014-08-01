(defn alength
  "Returns the length of the Java array. Works on arrays of all
  types."
  {:inline (fn [a] `(. clojure.lang.RT (alength ~a)))
   :added "1.0"}
  [array] (. clojure.lang.RT (alength array)))