;; create an array of Java Objects using object-array
;; and demonstrate that it can be used with the Java fill function

user=> (def os (object-array [nil 23.2 "abc" 33]))
#'user/os
user=> (vec os)
[nil 23.2 "abc" 33]
user=> (java.util.Arrays/fill os 31415)
nil
user=> (vec os)
[31415 31415 31415 31415]
user=>