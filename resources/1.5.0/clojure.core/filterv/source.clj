(defn filterv
  "Returns a vector of the items in coll for which
  (pred item) returns true. pred must be free of side-effects."
  {:added "1.4"
   :static true}
  [pred coll]
  (-> (reduce (fn [v o] (if (pred o) (conj! v o) v))
              (transient [])
              coll)
      persistent!))