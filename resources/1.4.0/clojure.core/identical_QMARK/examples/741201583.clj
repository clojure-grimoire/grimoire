user=> (def x {:foo 1, :bar -3})
#'user/x
user=> (def y {:foo 1, :bar -3})
#'user/y
;; Values are equal, but different objects were constructed
user=> (= x y)
true
user=> (identical? x y)
false
