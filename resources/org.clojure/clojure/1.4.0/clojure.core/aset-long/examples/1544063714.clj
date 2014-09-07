;; create an array of 10 longs and set one of the values to 31415

user=> (def ls (long-array 10))
#'user/ls
user=> (vec ls)
[0 0 0 0 0 0 0 0 0 0]
user=> (aset-long ls 3 31415)
31415
user=> (vec ls)
[0 0 0 31415 0 0 0 0 0 0]
user=>