;; create a function that simulates a long running process using Thread/sleep
(defn long-running-job [n]
    (Thread/sleep 3000) ; wait for 3 seconds
    (+ n 10))

;; used `doall` to eagerly evaluate `map`, which evaluates lazily by default

;; notice that the total elapse time is almost 3 secs * 4
user=> (time (doall (map long-running-job (range 4))))
"Elapsed time: 11999.235098 msecs"
(10 11 12 13)

;; notice that the total elapse time is almost 3 secs only
user=> (time (doall (pmap long-running-job (range 4))))
"Elapsed time: 3200.001117 msecs"
(10 11 12 13)