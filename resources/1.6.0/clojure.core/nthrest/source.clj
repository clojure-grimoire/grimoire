(defn nthrest
  "Returns the nth rest of coll, coll when n is 0."
  {:added "1.3"
   :static true}
  [coll n]
    (loop [n n xs coll]
      (if (and (pos? n) (seq xs))
        (recur (dec n) (rest xs))
        xs)))