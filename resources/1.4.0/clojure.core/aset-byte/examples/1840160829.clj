;; create an array of 10 bytes and set one of the values to 127

user=> (def bs (byte-array 10))
#'user/bs
user=> (vec bs)
[0 0 0 0 0 0 0 0 0 0]
user=> (aset-byte bs 2 127)
127
user=> (vec bs)
[0 0 127 0 0 0 0 0 0 0]
user=>