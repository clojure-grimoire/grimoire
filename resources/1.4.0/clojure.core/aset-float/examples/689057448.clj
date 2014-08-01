;; create an array of 10 floats and set one of the values to 3.1415

user=> (def fs (float-array 10))
#'user/fs
user=> (vec fs)
[0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0]
user=> (aset-float fs 3 3.1415)
3.1415
user=> (vec fs)
[0.0 0.0 0.0 3.1415 0.0 0.0 0.0 0.0 0.0 0.0]
user=>