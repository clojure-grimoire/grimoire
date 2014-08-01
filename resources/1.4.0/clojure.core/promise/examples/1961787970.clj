;; Create a promise
user> (def p (promise))
#'user/p ; p is our promise

;; Check if was delivered/realized
user> (realized? p)
false ; No yet

;; Delivering the promise
user> (deliver p 42)
#<core$promise$reify__5727@47122d: 42>

;; Check again if it was delivered
user> (realized? p)
true ; Yes!

;; Deref to see what has been delivered
user> @p
42

;; Note that @ is shorthand for deref
user> (deref p)
42
