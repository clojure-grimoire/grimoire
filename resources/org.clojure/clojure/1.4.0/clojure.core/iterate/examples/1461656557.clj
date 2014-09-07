;; demonstrating the power of iterate
;; to generate the Fibonacci sequence
;; uses +' to promote to BigInt
user=> (def fib (map first (iterate (fn [[a b]] [b (+' a b)]) [0 1])))
#'user/fib

user=> (take 10 fib)
(0 1 1 2 3 5 8 13 21 34)