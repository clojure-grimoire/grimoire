user=> (def o (Object.))

user=> (future (locking o 
                 (Thread/sleep 10000) 
                 (println "done")))

;; Now run this again before 10 seconds is up and you'll 
;; find the second instance prints done 10 seconds after the 
;; first instance has released the lock

user=> (future (locking o 
                 (Thread/sleep 10000) 
                 (println "done")))

;; Operates like the synchronized keyword in Java.
