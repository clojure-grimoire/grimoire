;; The "in-ns" function works almost the same as "ns", but does not load clojure.core 

user=> (in-ns 'my-namespace)
#&lt;Namespace my-namespace&gt;

;; the function clojure.core/inc won't just work
my-namespace=> (inc 1)
java.lang.Exception: Unable to resolve symbol: inc in this context (NO_SOURCE_FILE:15)

my-namespace=> (clojure.core/inc 1)
2
