;; create an array of 10 booleans and set one value to true
;; using aset-boolean

user=> (def bs (boolean-array 10))
#'user/bs
user=> (vec bs)
[false false false false false false false false false false]
user=> (aset-boolean bs 2 true)
true
user=> (vec bs)
[false false true false false false false false false false]
user=>