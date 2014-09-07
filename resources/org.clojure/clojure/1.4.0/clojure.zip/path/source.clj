(defn path
  "Returns a seq of nodes leading to this loc"
  {:added "1.0"}
  [loc]
    (:pnodes (loc 1)))