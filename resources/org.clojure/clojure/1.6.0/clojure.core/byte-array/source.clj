(defn byte-array
  "Creates an array of bytes"
  {:inline (fn [& args] `(. clojure.lang.Numbers byte_array ~@args))
   :inline-arities #{1 2}
   :added "1.1"}
  ([size-or-seq] (. clojure.lang.Numbers byte_array size-or-seq))
  ([size init-val-or-seq] (. clojure.lang.Numbers byte_array size init-val-or-seq)))