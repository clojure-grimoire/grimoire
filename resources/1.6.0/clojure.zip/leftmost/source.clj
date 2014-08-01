(defn leftmost
  "Returns the loc of the leftmost sibling of the node at this loc, or self"
  {:added "1.0"}
  [loc]
    (let [[node {l :l r :r :as path}] loc]
      (if (and path (seq l))
        (with-meta [(first l) (assoc path :l [] :r (concat (rest l) [node] r))] (meta loc))
        loc)))