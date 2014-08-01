(defn right
  "Returns the loc of the right sibling of the node at this loc, or nil"
  {:added "1.0"}
  [loc]
    (let [[node {l :l  [r & rnext :as rs] :r :as path}] loc]
      (when (and path rs)
        (with-meta [r (assoc path :l (conj l node) :r rnext)] (meta loc)))))