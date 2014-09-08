;; create an array of Java boolean's using boolean-array
;; and demonstrate that it can be used for input into the standard
;; Java Arrays.fill function

user=> (def bs (boolean-array (map even? (range 3 10))))
#'user/bs
user=> (type bs)
[Z
user=> (vec bs)
[false true false true false true false]
user=> (java.util.Arrays/fill bs 3 7 false)
nil
user=> (vec bs)
[false true false false false false false]
user=>