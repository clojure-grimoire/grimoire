(defn assoc!
  "Alpha - subject to change.
  When applied to a transient map, adds mapping of key(s) to
  val(s). When applied to a transient vector, sets the val at index.
  Note - index must be <= (count vector). Returns coll."
  {:added "1.1"
   :static true}
  ([^clojure.lang.ITransientAssociative coll key val] (.assoc coll key val))
  ([^clojure.lang.ITransientAssociative coll key val & kvs]
   (let [ret (.assoc coll key val)]
     (if kvs
       (recur ret (first kvs) (second kvs) (nnext kvs))
       ret))))