(defn disj!
  "disj[oin]. Returns a transient set of the same (hashed/sorted) type, that
  does not contain key(s)."
  {:added "1.1"
   :static true}
  ([set] set)
  ([^clojure.lang.ITransientSet set key]
   (. set (disjoin key)))
  ([^clojure.lang.ITransientSet set key & ks]
   (let [ret (. set (disjoin key))]
     (if ks
       (recur ret (first ks) (next ks))
       ret))))