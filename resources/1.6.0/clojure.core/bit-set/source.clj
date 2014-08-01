(defn bit-set
  "Set bit at index n"
  {:added "1.0"
   :static true}
  [x n] (. clojure.lang.Numbers setBit x n))