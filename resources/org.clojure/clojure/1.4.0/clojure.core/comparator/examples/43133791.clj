;; simple example to create an ArrayList, initially [1,2,0]
;; and sort it in descending order

user=> (def a (new java.util.ArrayList [1 2 0]))
#'user/a
user=> (def compx (comparator (fn [x y] (> x y))))
#'user/compx
user=> (java.util.Collections/sort a compx)
nil
user=> a
#<ArrayList [2, 1, 0]>

;; Note however that 'comparator' is rarely (never?) needed because if
;; the fn returns a boolean, the .compare implementation Clojure provides
;; causes it to behave the same as if 'comparator' were wrapped around it:

(sort (comparator (fn [x y] (> x y))) [1 2 0]) ;=> (2 1 0)
(sort (fn [x y] (> x y)) [1 2 0])              ;=> (2 1 0)
(sort > [1 2 0])                               ;=> (2 1 0)
(sort < [1 2 0])                               ;=> (0 1 2)