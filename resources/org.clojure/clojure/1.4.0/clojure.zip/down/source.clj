(defn down
  "Returns the loc of the leftmost child of the node at this loc, or
  nil if no children"
  {:added "1.0"}
  [loc]
    (when (branch? loc)
      (let [[node path] loc
            [c & cnext :as cs] (children loc)]
        (when cs
          (with-meta [c {:l [] 
                         :pnodes (if path (conj (:pnodes path) node) [node]) 
                         :ppath path 
                         :r cnext}] (meta loc))))))