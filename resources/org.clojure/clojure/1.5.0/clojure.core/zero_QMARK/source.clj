(defn zero?
  "Returns true if num is zero, else false"
  {
   :inline (fn [x] `(. clojure.lang.Numbers (isZero ~x)))
   :added "1.0"}
  [x] (. clojure.lang.Numbers (isZero x)))