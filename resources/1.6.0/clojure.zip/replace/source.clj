(defn replace
  "Replaces the node at this loc, without moving"
  {:added "1.0"}
  [loc node]
    (let [[_ path] loc]
      (with-meta [node (assoc path :changed? true)] (meta loc))))