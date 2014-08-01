user=> (def x 1)
#'user/x
user=> (identical? x x)
true
user=> (identical? x 1)
true
user=> (identical? x 2)
false
user=> (identical? x ((constantly 1) 8))
true
user=> (identical? 'a 'a)
false