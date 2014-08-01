user=> (def foobar)
#'user/foobar
user=> (bound? #'foobar)
false
user=> (def boing 10)
#'user/boing
user=> (bound? #'boing)
true
user=> (defn plus3 [n] (+ 3 n))
#'user/plus3
user=> (bound? #'plus3)
true
