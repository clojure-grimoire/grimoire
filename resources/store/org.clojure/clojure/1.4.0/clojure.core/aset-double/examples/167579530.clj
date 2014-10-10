;; create an array of 10 doubles and set one of the elements to 3.1415

user=> (def ds (double-array 10))
#'user/ds
user=> (vec ds)
[0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0]
user=> (aset-double ds 3 3.1415)
3.1415
user=> (vec ds)
[0.0 0.0 0.0 3.1415 0.0 0.0 0.0 0.0 0.0 0.0]
user=>