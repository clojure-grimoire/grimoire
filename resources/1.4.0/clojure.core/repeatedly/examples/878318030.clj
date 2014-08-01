;these two functions are equivalent 

user=> (take 5 (repeatedly #(rand-int 11)))
(6 6 3 9 8)

user=> (repeatedly 5 #(rand-int 11))
(1 8 6 9 6)

;compare with repeat
user=> (repeat 5 (rand-int 100))
(94 94 94 94 94)