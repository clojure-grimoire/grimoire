(defn dec'
  "Returns a number one less than num. Supports arbitrary precision.
  See also: dec"
  {:inline (fn [x] `(. clojure.lang.Numbers (decP ~x)))
   :added "1.0"}
  [x] (. clojure.lang.Numbers (decP x)))