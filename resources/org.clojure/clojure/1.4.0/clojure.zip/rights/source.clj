(defn rights
  "Returns a seq of the right siblings of this loc"
  {:added "1.0"}
  [loc]
    (:r (loc 1)))