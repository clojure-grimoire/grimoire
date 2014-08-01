(defn postwalk-replace
  "Recursively transforms form by replacing keys in smap with their
  values.  Like clojure/replace but works on any data structure.  Does
  replacement at the leaves of the tree first."
  {:added "1.1"}
  [smap form]
  (postwalk (fn [x] (if (contains? smap x) (smap x) x)) form))