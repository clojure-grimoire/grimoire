user> (def a (to-array-2d [[1 2 3][4 5 6]]))
#'user/a
user> (alength a)
2
user> (alength (aget a 0))
3
user> (aget a 0 0)
1
user> (aget a 0 1)
2
user> (aget a 0 2)
3
user> (aget a 1 0)
4
user> (aget a 2 0)
â†’ ERROR
nil

user> 