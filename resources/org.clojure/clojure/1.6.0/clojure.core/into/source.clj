(defn into
  "Returns a new coll consisting of to-coll with all of the items of
  from-coll conjoined."
  {:added "1.0"
   :static true}
  [to from]
  (if (instance? clojure.lang.IEditableCollection to)
    (with-meta (persistent! (reduce conj! (transient to) from)) (meta to))
    (reduce conj to from)))