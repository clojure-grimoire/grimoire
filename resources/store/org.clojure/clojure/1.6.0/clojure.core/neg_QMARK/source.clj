(defn neg?
  "Returns true if num is less than zero, else false"
  {
   :inline (fn [x] `(. clojure.lang.Numbers (isNeg ~x)))
   :added "1.0"}
  [x] (. clojure.lang.Numbers (isNeg x)))