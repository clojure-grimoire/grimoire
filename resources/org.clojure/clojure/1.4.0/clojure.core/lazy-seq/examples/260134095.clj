;; A lazy-seq of Fibonacci numbers (fn = fn-1 + fn-2)
;; The producer function takes exactly two parameters
;; (because we need the last 2 elements to produce a new one)
user=> (defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))

user=> (take 5 (fib 1 1))
(1 1 2 3 5)