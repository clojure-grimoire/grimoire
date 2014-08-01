user=> (let [start (System/nanoTime)
             q (seque
                 (iterate
                   #(do (Thread/sleep 400) (inc %))
                   0))]
         (println "sleep five seconds...")
         (Thread/sleep 5000)
         (doseq [i (take 20 q)]
           (println (int (/ (- (System/nanoTime) start) 1e7))
                    ":" i)))


;; The iterate form returns a lazy seq that delays nearly a half-second 
;; before returning each subsequent item.  Here seque starts a thread 
;; generating the lazy seq.

;; The body of the let allows the seque thread to get ahead by five seconds
;; before it begins consuming the seq using doseq.  The doseq prints a 
;; timestamp and the value from the seq when it becomes available.  The
;; first 11 or so are available almost instantly, until the consuming 
;; doseq catches up with the producing iterate, at which point the consumer
;; blocks for 400ms before each item can be printed.

;;sleep five seconds...
500 : 0
500 : 1
500 : 2
500 : 3
500 : 4
500 : 5
500 : 6
500 : 7
500 : 8
500 : 9
500 : 10
500 : 11
520 : 12
560 : 13
600 : 14
640 : 15
680 : 16
720 : 17
760 : 18
800 : 19

