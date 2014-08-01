user> (def counter (atom 0))
#'user/counter

user> (swap! counter inc)
1

user> (swap! counter inc)
2