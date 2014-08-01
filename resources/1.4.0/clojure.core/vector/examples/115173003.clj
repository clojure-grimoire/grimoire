;; create an empty vector the long way
user=> (vector)
[]

;; create an empty vector the short way
user=> []
[]

;; you can even create vectors with nil values
user=> (vector nil)
[nil]

;; create a vector the long way
user=> (vector 1 2 3)
[1 2 3]

;; create a vector the short way
user=> [1 2 3]
[1 2 3]

;; checking for the 2 results above
user=> (class (vector 1 2 3))
clojure.lang.PersistentVector

user=> (class [1 2 3])
clojure.lang.PersistentVector

user=> (= (vector 1 2 3) [1 2 3])
true

