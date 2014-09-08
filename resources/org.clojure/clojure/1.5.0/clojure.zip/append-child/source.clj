(defn append-child
  "Inserts the item as the rightmost child of the node at this loc,
  without moving"
  {:added "1.0"}
  [loc item]
    (replace loc (make-node loc (node loc) (concat (children loc) [item]))))