(defn edit
  "Replaces the node at this loc with the value of (f node args)"
  {:added "1.0"}
  [loc f & args]
    (replace loc (apply f (node loc) args)))