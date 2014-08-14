(defn char-array
  "Creates an array of chars"
  {:inline (fn [& args] `(. clojure.lang.Numbers char_array ~@args))
   :inline-arities #{1 2}
   :added "1.1"}
  ([size-or-seq] (. clojure.lang.Numbers char_array size-or-seq))
  ([size init-val-or-seq] (. clojure.lang.Numbers char_array size init-val-or-seq)))