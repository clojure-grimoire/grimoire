(defn dissoc!
  "Returns a transient map that doesn't contain a mapping for key(s)."
  {:added "1.1"
   :static true}
  ([^clojure.lang.ITransientMap map key] (.without map key))
  ([^clojure.lang.ITransientMap map key & ks]
   (let [ret (.without map key)]
     (if ks
       (recur ret (first ks) (next ks))
       ret))))