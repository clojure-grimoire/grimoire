(defn left
  "Returns the loc of the left sibling of the node at this loc, or nil"
  {:added "1.0"}
  [loc]
    (let [[node {l :l r :r :as path}] loc]
      (when (and path (seq l))
        (with-meta [(peek l) (assoc path :l (pop l) :r (cons node r))] (meta loc)))))