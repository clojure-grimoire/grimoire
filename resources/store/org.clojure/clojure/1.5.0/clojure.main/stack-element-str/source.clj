(defn stack-element-str
  "Returns a (possibly unmunged) string representation of a StackTraceElement"
  {:added "1.3"}
  [^StackTraceElement el]
  (let [file (.getFileName el)
        clojure-fn? (and file (or (.endsWith file ".clj")
                                  (= file "NO_SOURCE_FILE")))]
    (str (if clojure-fn?
           (demunge (.getClassName el))
           (str (.getClassName el) "." (.getMethodName el)))
         " (" (.getFileName el) ":" (.getLineNumber el) ")")))