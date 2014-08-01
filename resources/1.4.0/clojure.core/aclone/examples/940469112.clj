;; create an Java integer array, then clone it
;; note that when you modify b, a remains the same
;; showing that b is not just a reference to a

user=> (def a (int-array [1 2 3 4]))
#'user/a
user=> (def b (aclone a))
#'user/b
user=> (aset b 0 23)
23
user=> (vec b)
[23 2 3 4]
user=> (vec a)
[1 2 3 4]