;; Create a namespace named demo.namespace.
(ns demo.namespace)

;; Clojure recommends namespaces be at least "two segments" (ie, they should have at least one '.') otherwise it will create a class in the "default package", which is discouraged.

;; If this declaration appears in a file named "demo/namespace.clj" present in your classpath, it is known as a "lib", "demo/namespace.clj" is the lib's "root resource". See http://clojure.org/libs

;; From a clean repl you can load the lib using
user=>(require 'demo.namespace) 
; or
user=>(use 'demo.namespace)