user=> (def counter (ref 0))
#'user/counter

;; deciding whether to increment the counter takes the terribly long time
;; of 100 ms -- it is decided by committee.
user=> (defn commute-inc! [counter]
         (dosync (Thread/sleep 100) (commute counter inc)))
#'user/commute-inc!
user=> (defn alter-inc! [counter]
         (dosync (Thread/sleep 100) (alter counter inc)))
#'user/alter-inc!

;; what if n people try to hit the counter at once?
user=> (defn bombard-counter! [n f counter]
         (apply pcalls (repeat n #(f counter))))
#'user/bombard-counter!

;; first, use alter.  Everyone is trying to update the counter, and
;; stepping on each other's toes, so almost every transaction is getting 
;; retried lots of times:
user=> (dosync (ref-set counter 0))
0
user=> (time (doall (bombard-counter! 20 alter-inc! counter)))
"Elapsed time: 2007.049224 msecs"
(3 1 2 4 7 10 5 8 6 9 13 14 15 12 11 16 17 20 18 19)
;; note that it took about 2000 ms = (20 workers * 100 ms / update)

;; now, since it doesn't matter what order people update a counter in, we
;; use commute:
user=> (dosync (ref-set counter 0))
0
user=> (time (doall (bombard-counter! 20 commute-inc! counter)))
"Elapsed time: 401.748181 msecs"
(1 2 3 4 5 9 10 6 7 8 11 15 13 12 14 16 19 17 18 20)
;; notice that we got actual concurrency this time.