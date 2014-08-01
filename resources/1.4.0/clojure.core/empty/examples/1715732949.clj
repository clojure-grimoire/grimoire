;; The output will not necessarily be of the same JVM class as the input
user=> (class (seq [1]))
clojure.lang.PersistentVector$ChunkedSeq

user=> (class (empty (seq [1])))
clojure.lang.PersistentList$EmptyList
