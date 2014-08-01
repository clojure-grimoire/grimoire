(defn rightmost
  "Returns the loc of the rightmost sibling of the node at this loc, or self"
  {:added "1.0"}
  [loc]
    (let [[node {l :l r :r :as path}] loc]
      (if (and path r)
        (with-meta [(last r) (assoc path :l (apply conj l node (butlast r)) :r nil)] (meta loc))
        loc)))