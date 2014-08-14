(defn repl-exception
  "Returns the root cause of throwables"
  [throwable]
  (root-cause throwable))