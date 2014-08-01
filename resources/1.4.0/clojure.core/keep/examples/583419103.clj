;; Sieve of Eratosthenes by using 'keep'.

(defn keep-mcdr [f coll]
  (lazy-seq
     (when-let [x (first coll)]
       (cons x  (keep-mcdr f (f x (rest coll)))))))

(defn prime-number [n]
  (cons 1
	(keep-mcdr
	 (fn[x xs] (if (not-empty xs)
		     (keep #(if-not (zero? (rem % x)) %)
			   xs)))
	 (range 2 n))))

user> (prime-number 100)
(1 2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71 73 79 83 89 97)
