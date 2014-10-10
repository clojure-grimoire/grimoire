;; Create a string from the given items and store it in x.
user=> (def x (print-str 1 "foo" \b \a \r {:a 2}))
#'user/x

;; It's a string.
user=> (string? x)
true

;; Notice that each item is separated by a space.
user=> x
"1 foo b a r {:a 2}"

