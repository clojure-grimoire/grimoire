;; create an array of shorts using short-array
;; and demonstrate how it can be used with the java Arrays functions
;; (note the needed coercions)

user=> (def ss (short-array (map short (range 3 10))))
#'user/ss
user=> (type ss)
[S
user=> (vec ss)
[3 4 5 6 7 8 9]
user=> (java.util.Arrays/binarySearch ss (short 6))
3
user=> (java.util.Arrays/fill ss (short 99))
nil
user=> (vec ss)
[99 99 99 99 99 99 99]
user=>