(defn munge [s]
  ((if (symbol? s) symbol str) (clojure.lang.Compiler/munge (str s))))