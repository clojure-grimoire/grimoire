user=> (remove pos? [1 -2 2 -1 3 7 0])
(-2 -1 0)

user=> (remove #(zero? (mod % 3)) (range 1 21))
(1 2 4 5 7 8 10 11 13 14 16 17 19 20)