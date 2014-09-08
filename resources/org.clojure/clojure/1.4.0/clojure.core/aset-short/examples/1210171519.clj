;; create an array of 10 shorts and set one of the values to 31415

user=> (def ss (short-array 10))
#'user/ss
user=> (vec ss)
[0 0 0 0 0 0 0 0 0 0]
user=> (aset-short ss 3 31415)
31415
user=> (vec ss)
[0 0 0 31415 0 0 0 0 0 0]
user=>