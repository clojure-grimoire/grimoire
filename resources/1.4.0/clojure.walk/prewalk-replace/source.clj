(defn prewalk-replace
  "Recursively transforms form by replacing keys in smap with their
  values.  Like clojure/replace but works on any data structure.  Does
  replacement at the root of the tree first."
  {:added "1.1"}
  [smap form]
  (prewalk (fn [x] (if (contains? smap x) (smap x) x)) form))