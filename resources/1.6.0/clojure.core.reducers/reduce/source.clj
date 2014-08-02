(defn reduce
  "Like core/reduce except:
     When init is not provided, (f) is used.
     Maps are reduced with reduce-kv"
  ([f coll] (reduce f (f) coll))
  ([f init coll]
     (if (instance? java.util.Map coll)
       (clojure.core.protocols/kv-reduce coll f init)
       (clojure.core.protocols/coll-reduce coll f init))))