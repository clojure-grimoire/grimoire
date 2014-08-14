;; An example combining lazy sequences with higher order functions
;; Generate prime numbers using trial division.
;; Note that the starting set of sieved numbers should be
;; the set of integers starting with 2 i.e., (iterate inc 2) 
(defn sieve [s]
  (cons (first s)
        (lazy-seq (sieve (filter #(not= 0 (mod % (first s)))
                                 (rest s))))))

user=> (take 20 (sieve (iterate inc 2)))
(2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71)
