
user=> (conj (vector-of :int) 1 2 3)
[1 2 3]  ; <-- note, these are unboxes internally

user=> (type (conj (vector-of :int) 1 2 3))
clojure.core.Vec
