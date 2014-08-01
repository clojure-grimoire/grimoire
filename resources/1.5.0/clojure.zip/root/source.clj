(defn root
  "zips all the way up and returns the root node, reflecting any
 changes."
  {:added "1.0"}
  [loc]
    (if (= :end (loc 1))
      (node loc)
      (let [p (up loc)]
        (if p
          (recur p)
          (node loc)))))