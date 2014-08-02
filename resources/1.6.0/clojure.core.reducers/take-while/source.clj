(defcurried take-while
  "Ends the reduction of coll when (pred val) returns logical false."
  {:added "1.5"}
  [pred coll]
  (reducer coll
   (fn [f1]
     (rfn [f1 k]
          ([ret k v]
             (if (pred k v)
               (f1 ret k v)
               (reduced ret)))))))