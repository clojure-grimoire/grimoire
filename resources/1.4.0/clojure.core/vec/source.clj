(defn vec
  "Creates a new vector containing the contents of coll."
  {:added "1.0"
   :static true}
  ([coll]
   (if (instance? java.util.Collection coll)
     (clojure.lang.LazilyPersistentVector/create coll)
     (. clojure.lang.LazilyPersistentVector (createOwning (to-array coll))))))