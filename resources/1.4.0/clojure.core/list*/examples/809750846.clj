;; `list*` function:
user=> (list* 1 [2 3])
(1 2 3)
user=> (list* 1 2 [3 4])
(1 2 3 4)

;; compared to regular `list` function:
user=> (list 1 [2 3])
(1 [2 3])
user=> (list 1 2 [3 4])
(1 2 [3 4])

;; Corner cases:
user=> (list* nil [1 2])
(nil 1 2)
user=> (list* 1 nil)
(1)
user=> (list* () [1 2])
(() 1 2)
user=> (list* 1 ())
(1)
