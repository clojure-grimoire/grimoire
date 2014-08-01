user=> (def powers-of-two (iterate (partial * 2) 1))
#'user/powers-of-two

user=> (nth powers-of-two 10)
1024
user=> (take 10 powers-of-two)
(1 2 4 8 16 32 64 128 256 512)
