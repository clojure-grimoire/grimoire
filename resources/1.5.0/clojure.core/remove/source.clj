(defn remove
  "Returns a lazy sequence of the items in coll for which
  (pred item) returns false. pred must be free of side-effects."
  {:added "1.0"
   :static true}
  [pred coll]
  (filter (complement pred) coll))