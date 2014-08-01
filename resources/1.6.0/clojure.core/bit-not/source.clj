(defn bit-not
  "Bitwise complement"
  {:inline (fn [x] `(. clojure.lang.Numbers (not ~x)))
   :added "1.0"}
  [x] (. clojure.lang.Numbers not x))