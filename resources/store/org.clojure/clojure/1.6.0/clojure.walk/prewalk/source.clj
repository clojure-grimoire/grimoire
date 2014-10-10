(defn prewalk
  "Like postwalk, but does pre-order traversal."
  {:added "1.1"}
  [f form]
  (walk (partial prewalk f) identity (f form)))