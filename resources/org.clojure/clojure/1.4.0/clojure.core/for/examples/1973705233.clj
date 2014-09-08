;; More examples illustrating the difference between :when and :while

;; Simple but inefficient method of checking whether a number is
;; prime.
user=> (defn prime? [n]
         (not-any? zero? (map #(rem n %) (range 2 n))))
#'user/prime?

user=> (range 3 33 2)
(3 5 7 9 11 13 15 17 19 21 23 25 27 29 31)

;; :when continues through the collection even if some have the
;; condition evaluate to false, like filter
user=> (for [x (range 3 33 2) :when (prime? x)]
         x)
(3 5 7 11 13 17 19 23 29 31)

;; :while stops at the first collection element that evaluates to
;; false, like take-while
user=> (for [x (range 3 33 2) :while (prime? x)]
         x)
(3 5 7)

;; The examples above can easily be rewritten with filter or
;; take-while.  When you have a for with multiple binding forms, so
;; that the iteration occurs in a nested fashion, it becomes possible
;; to write something briefly with 'for' that would be more verbose or
;; unwieldy with nested filter or take-while expressions.

user=> (for [x (range 3 17 2) :when (prime? x)
             y (range 3 17 2) :when (prime? y)]
         [x y])
([ 3 3] [ 3 5] [ 3 7] [ 3 11] [ 3 13]
 [ 5 3] [ 5 5] [ 5 7] [ 5 11] [ 5 13]
 [ 7 3] [ 7 5] [ 7 7] [ 7 11] [ 7 13]
 [11 3] [11 5] [11 7] [11 11] [11 13]
 [13 3] [13 5] [13 7] [13 11] [13 13])

user=> (for [x (range 3 17 2) :while (prime? x)
             y (range 3 17 2) :while (prime? y)]
         [x y])
([3 3] [3 5] [3 7]
 [5 3] [5 5] [5 7]
 [7 3] [7 5] [7 7])

;; This example only gives a finite result because of the :while
;; expressions.
user=> (for [x (range) :while (< x 10) 
             y (range) :while (<= y x)]
         [x y])

([0 0]
 [1 0] [1 1]
 [2 0] [2 1] [2 2]
 [3 0] [3 1] [3 2] [3 3]
 [4 0] [4 1] [4 2] [4 3] [4 4]
 [5 0] [5 1] [5 2] [5 3] [5 4] [5 5]
 [6 0] [6 1] [6 2] [6 3] [6 4] [6 5] [6 6]
 [7 0] [7 1] [7 2] [7 3] [7 4] [7 5] [7 6] [7 7]
 [8 0] [8 1] [8 2] [8 3] [8 4] [8 5] [8 6] [8 7] [8 8]
 [9 0] [9 1] [9 2] [9 3] [9 4] [9 5] [9 6] [9 7] [9 8] [9 9])
