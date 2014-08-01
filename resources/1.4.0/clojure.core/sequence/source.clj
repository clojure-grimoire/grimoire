(defn sequence
  "Coerces coll to a (possibly empty) sequence, if it is not already
  one. Will not force a lazy seq. (sequence nil) yields ()"
  {:added "1.0"
   :static true}
  [coll]
   (if (seq? coll) coll
    (or (seq coll) ())))