user> (defonce foo 5)
#'user/foo

user> foo
5

;; defonce does nothing the second time
user> (defonce foo 10)
nil

user> foo
5