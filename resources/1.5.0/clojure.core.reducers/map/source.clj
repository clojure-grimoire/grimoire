(defcurried map
  "Applies f to every value in the reduction of coll. Foldable."
  {:added "1.5"}
  [f coll]
  (folder coll
   (fn [f1]
     (rfn [f1 k]
          ([ret k v]
             (f1 ret (f k v)))))))