(defn short-array
  "Creates an array of shorts"
  {:inline (fn [& args] `(. clojure.lang.Numbers short_array ~@args))
   :inline-arities #{1 2}
   :added "1.1"}
  ([size-or-seq] (. clojure.lang.Numbers short_array size-or-seq))
  ([size init-val-or-seq] (. clojure.lang.Numbers short_array size init-val-or-seq)))