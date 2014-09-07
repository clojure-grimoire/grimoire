(defn insert-right
  "Inserts the item as the right sibling of the node at this loc,
  without moving"
  {:added "1.0"}
  [loc item]
    (let [[node {r :r :as path}] loc]
      (if (nil? path)
        (throw (new Exception "Insert at top"))
        (with-meta [node (assoc path :r (cons item r) :changed? true)] (meta loc)))))