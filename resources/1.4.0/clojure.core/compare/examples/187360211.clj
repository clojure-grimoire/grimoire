;; various examples
;; comparing vectors of different sizes does not work as you may expect
;; the longer vector is always "greater" regardless of contents 

user=> (compare [0 1 2] [0 1 2])
0
user=> (compare [1 2 3] [0 1 2 3])
-1
user=> (compare [0 1 2] [3 4])
1
user=> (compare nil [1 2 3])
-1
user=> (compare [1 2 3] nil)
1
user=> (compare "abc" "def")
-3
user=> (compare "abc" "abd")
-1