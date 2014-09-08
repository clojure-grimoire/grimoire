
;; create two arrays
user=> (def a1 (double-array '(1.0 2.0 3.0 4.0)))
#'user/a1
user=> (def a2 (int-array '(9 8 7 6)))
#'user/a2

;; get an item by index
user=> (aget a1 2)
3.0
user=> (aget a2 3)
6

;; 2d array and 2 indicies
user=> (def a3 (make-array Integer/TYPE 100 100))
#'user/a3
user=> (aget a3 23 42)
0
