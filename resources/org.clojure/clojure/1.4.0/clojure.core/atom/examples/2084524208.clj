user=> (def my-atom (atom 0))
#'user/my-atom

user=> @my-atom
0

user=> (swap! my-atom inc)
1

user=> @my-atom
1

user=> (swap! my-atom (fn [n] (* (+ n n) 2)))
4