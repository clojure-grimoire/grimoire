(defn reductions
  "Returns a lazy seq of the intermediate values of the reduction (as
  per reduce) of coll by f, starting with init."
  {:added "1.2"}
  ([f coll]
     (lazy-seq
      (if-let [s (seq coll)]
        (reductions f (first s) (rest s))
        (list (f)))))
  ([f init coll]
     (cons init
           (lazy-seq
            (when-let [s (seq coll)]
              (reductions f (f init (first s)) (rest s)))))))