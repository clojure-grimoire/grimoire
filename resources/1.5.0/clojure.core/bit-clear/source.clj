(defn bit-clear
  "Clear bit at index n"
  {:added "1.0"
   :static true}
  [x n] (. clojure.lang.Numbers clearBit x n))