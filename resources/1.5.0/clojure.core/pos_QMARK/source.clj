(defn pos?
  "Returns true if num is greater than zero, else false"
  {
   :inline (fn [x] `(. clojure.lang.Numbers (isPos ~x)))
   :added "1.0"}
  [x] (. clojure.lang.Numbers (isPos x)))