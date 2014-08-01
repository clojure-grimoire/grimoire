;; quick example of a ragged array where the length of each element of the 
;; 2d array is unique

user=> (def a (to-array-2d [[0][1 2][3 4 5][6 7 8 9]]))
#'user/a
user=> (map alength [(aget a 0)(aget a 1)(aget a 2)])
(1 2 3)
user=>