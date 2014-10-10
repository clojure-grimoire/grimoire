(defcurried remove
  "Removes values in the reduction of coll for which (pred val)
  returns logical true. Foldable."
  {:added "1.5"}
  [pred coll]
  (filter (complement pred) coll))