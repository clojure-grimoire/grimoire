(defn end?
  "Returns true if loc represents the end of a depth-first walk"
  {:added "1.0"}
  [loc]
    (= :end (loc 1)))