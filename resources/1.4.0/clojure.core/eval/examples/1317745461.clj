user=> (def *foo* "(println [1 2 3])")
#'user/*foo*

user=> *foo*
"(println [1 2 3])"

user=> (eval *foo*)   ; Notice eval'ing a string does not work.
"(println [1 2 3])"

user=> (eval (read-string *foo*))
[1 2 3]
nil