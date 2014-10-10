;; the tarai benchmark comparing non-lazy version with lazy-version
(defn tarai [x y z]
  (if (<= (x) (y))
      (y)
      (recur (fn [] (tarai (fn [] (- (x) 1)) y z))
             (fn [] (tarai (fn [] (- (y) 1)) z x))
             (fn [] (tarai (fn [] (- (z) 1)) x y)))))

(defn tarai-d [x y z]
  (if (<= (force x) (force y))
      (force y)
      (recur (delay (tarai-d (- (force x) 1) y z))
               (delay (tarai-d (- (force y) 1) z x))
               (delay (tarai-d (- (force z) 1) x y)))))

user> (dotimes [_ 10] (time (tarai (fn [] 192) (fn [] 96) (fn [] 0))))
"Elapsed time: 139.660729 msecs"
"Elapsed time: 132.493587 msecs"
"Elapsed time: 135.867772 msecs"
"Elapsed time: 132.924774 msecs"
"Elapsed time: 137.491084 msecs"
"Elapsed time: 134.72752 msecs"
"Elapsed time: 132.969652 msecs"
"Elapsed time: 135.795754 msecs"
"Elapsed time: 134.261724 msecs"
"Elapsed time: 138.059968 msecs"

nil
user> (dotimes [_ 10 ]  (time (tarai-d 192 96 0)))
"Elapsed time: 3.181795 msecs"
"Elapsed time: 2.960096 msecs"
"Elapsed time: 3.000855 msecs"
"Elapsed time: 3.140536 msecs"
"Elapsed time: 3.658821 msecs"
"Elapsed time: 3.319659 msecs"
"Elapsed time: 2.9182 msecs"
"Elapsed time: 3.125442 msecs"
"Elapsed time: 2.944342 msecs"
"Elapsed time: 2.951613 msecs"
nil