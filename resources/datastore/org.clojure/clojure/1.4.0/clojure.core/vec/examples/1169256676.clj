;; Warning.  If the arg is a Java array, then the returned vector will alias it,
;; and modifying the array will thus modify the vector.  To avoid this, do
;; not modify the array after the vec call.  One way to guarantee this is to
;; make a copy of the array, call vec on the new array, and then lose all
;; references to the copy so it cannot be accessed in any way.

user=> (def a (to-array (repeat 4 0)))
#'user/a
user=> (seq a)
(0 0 0 0)
user=> (def v (vec a))
#'user/v
user=> v
[0 0 0 0]

;; Now change a, and v changes, too, since they share state.
user=> (aset a 2 -5)
-5
user=> v
[0 0 -5 0]

;; One way to avoid this
user=> (def v (vec (aclone a)))
#'user/v
user=> v
[0 0 -5 0]
user=> (aset a 2 -20)
-20
user=> v
[0 0 -5 0]
