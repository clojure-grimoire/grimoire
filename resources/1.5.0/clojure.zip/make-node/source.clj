(defn make-node
  "Returns a new branch node, given an existing node and new
  children. The loc is only used to supply the constructor."
  {:added "1.0"}
  [loc node children]
    ((:zip/make-node (meta loc)) node children))