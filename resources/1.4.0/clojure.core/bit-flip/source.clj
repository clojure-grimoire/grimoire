(defn bit-flip
  "Flip bit at index n"
  {:added "1.0"
   :static true}
  [x n] (. clojure.lang.Numbers flipBit x n))