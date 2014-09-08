(defn foldcat
  "Equivalent to (fold cat append! coll)"
  {:added "1.5"}
  [coll]
  (fold cat append! coll))