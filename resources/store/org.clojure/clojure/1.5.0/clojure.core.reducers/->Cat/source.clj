(deftype Cat [cnt left right]
  clojure.lang.Counted
  (count [_] cnt)

  clojure.lang.Seqable
  (seq [_] (concat (seq left) (seq right)))

  clojure.core.protocols/CollReduce
  (coll-reduce [this f1] (clojure.core.protocols/coll-reduce this f1 (f1)))
  (coll-reduce
   [_  f1 init]
   (clojure.core.protocols/coll-reduce
    right f1
    (clojure.core.protocols/coll-reduce left f1 init)))

  CollFold
  (coll-fold
   [_ n combinef reducef]
   (fjinvoke
    (fn []
      (let [rt (fjfork (fjtask #(coll-fold right n combinef reducef)))]
        (combinef
         (coll-fold left n combinef reducef)
         (fjjoin rt)))))))