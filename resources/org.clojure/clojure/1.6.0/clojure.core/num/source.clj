(defn num
  "Coerce to Number"
  {:tag Number
   :inline (fn  [x] `(. clojure.lang.Numbers (num ~x)))
   :added "1.0"}
  [x] (. clojure.lang.Numbers (num x)))