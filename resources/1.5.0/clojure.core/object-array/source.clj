(defn object-array
  "Creates an array of objects"
  {:inline (fn [arg] `(. clojure.lang.RT object_array ~arg))
   :inline-arities #{1}
   :added "1.2"}
  ([size-or-seq] (. clojure.lang.RT object_array size-or-seq)))