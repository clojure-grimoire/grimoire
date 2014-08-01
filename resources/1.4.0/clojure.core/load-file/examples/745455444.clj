;; Very useful from a REPL
;; Paths are specified as strings using canonical file path notation 
;; (rather than clojure-style namespaces dependent on the JVM classpath).
;; The working directory is set to wherever you invoked the JVM from, 
;; likely the project root.

(load-file "src/mylib/core.clj")

;; now you can go and evaluate vars defined in that file.