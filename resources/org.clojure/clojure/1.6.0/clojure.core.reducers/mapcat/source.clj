(defcurried mapcat
  "Applies f to every value in the reduction of coll, concatenating the result
  colls of (f val). Foldable."
  {:added "1.5"}
  [f coll]
  (folder coll
   (fn [f1]
     (let [f1 (fn
                ([ret v]
                  (let [x (f1 ret v)] (if (reduced? x) (reduced x) x)))
                ([ret k v]
                  (let [x (f1 ret k v)] (if (reduced? x) (reduced x) x))))]
       (rfn [f1 k]
            ([ret k v]
               (reduce f1 ret (f k v))))))))