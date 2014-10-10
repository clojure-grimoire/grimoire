(defn lefts
  "Returns a seq of the left siblings of this loc"
  {:added "1.0"}
  [loc]
    (seq (:l (loc 1))))