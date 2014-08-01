;; N.B. this example holds onto the head of a lazy seq which should generally be avoided
(def fib-seq
     (lazy-cat [0 1] (map + (rest fib-seq) fib-seq)))

(take 10 fib-seq)