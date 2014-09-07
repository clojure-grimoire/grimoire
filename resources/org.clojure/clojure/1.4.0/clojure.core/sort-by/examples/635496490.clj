;; Warning: You can sort a Java array and get back a sorted immutable Clojure
;; data structure, but it will also change the input Java array, by sorting it.
;; Copy the array before sorting if you want to avoid this.

user=> (def x (to-array [32 -5 4 11]))
#'user/x

user=> (seq x)
(32 -5 4 11)

user=> (def y (sort-by - x))
#'user/y

;; Return sorted sequence
user=> y
(32 11 4 -5)

;; but also modifies x, because it used the array to do the sorting.
user=> (seq x)
(32 11 4 -5)

;; One way to avoid this is copying the array before sorting:
user=> (def y (sort-by - (aclone x)))
#'user/y