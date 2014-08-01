(defn bit-test
  "Test bit at index n"
  {:added "1.0"
   :static true}
  [x n] (. clojure.lang.Numbers testBit x n))