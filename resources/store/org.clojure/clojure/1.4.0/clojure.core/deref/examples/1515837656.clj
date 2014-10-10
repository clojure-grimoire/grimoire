user=> (def a (atom 0))
#'user/a
user=> @a
0
user=> (deref a)
0

user=> (def b (ref 1))
#'user/b
user=> @b
1
user=> (deref b)
1

user=> (def c (agent 2))
#'user/c
user=> @c
2
user=> (deref c)
2

user=> (def d (future 3))
#'user/d
user=> @d
3
user=> (deref d)
3