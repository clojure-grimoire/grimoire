(defcurried flatten
  "Takes any nested combination of sequential things (lists, vectors,
  etc.) and returns their contents as a single, flat foldable
  collection."
  {:added "1.5"}
  [coll]
  (folder coll
   (fn [f1]
     (fn
       ([] (f1))
       ([ret v]
          (if (sequential? v)
            (clojure.core.protocols/coll-reduce (flatten v) f1 ret)
            (f1 ret v)))))))