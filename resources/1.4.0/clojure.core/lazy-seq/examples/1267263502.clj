;; The following defines a lazy-seq of all positive numbers.  Note that 
;; the lazy-seq allows us to make a recursive call in a safe way because
;; the call does not happen immediately but instead creates a closure.

user=> (defn positive-numbers 
	([] (positive-numbers 1))
	([n] (cons n (lazy-seq (positive-numbers (inc n))))))
#'user/positive-numbers

user=> (take 5 (positive-numbers))
(1 2 3 4 5)

