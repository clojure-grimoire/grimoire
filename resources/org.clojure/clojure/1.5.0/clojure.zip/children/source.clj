(defn children
  "Returns a seq of the children of node at loc, which must be a branch"
  {:added "1.0"}
  [loc]
    (if (branch? loc)
      ((:zip/children (meta loc)) (node loc))
      (throw (Exception. "called children on a leaf node"))))