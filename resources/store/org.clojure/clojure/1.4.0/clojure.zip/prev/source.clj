(defn prev
  "Moves to the previous loc in the hierarchy, depth-first. If already
  at the root, returns nil."
  {:added "1.0"}
  [loc]
    (if-let [lloc (left loc)]
      (loop [loc lloc]
        (if-let [child (and (branch? loc) (down loc))]
          (recur (rightmost child))
          loc))
      (up loc)))