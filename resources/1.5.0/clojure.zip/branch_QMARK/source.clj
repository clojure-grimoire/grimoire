(defn branch?
  "Returns true if the node at loc is a branch"
  {:added "1.0"}
  [loc]
    ((:zip/branch? (meta loc)) (node loc)))