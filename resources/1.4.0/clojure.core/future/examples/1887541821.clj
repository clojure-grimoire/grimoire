;; save the example in a script (e.g. test-future.clj) then run it in the console
;;
;; > clojure test-future.clj

(println "[Main] calculate the answer to life the universe and everything")

;; Used Thread/sleep to simulate long running process
(def what-is-the-answer-to-life (future 
        (println "[Future] started computation")
        (Thread/sleep 3000) ;; running for 3 seconds
        (println "[Future] completed computation")
        42))
        
(println "[Main] created future")

(Thread/sleep 1000)
(println "[Main] do other things while waiting for the answer")
(println "[Main] get the answer")
(println "[Main] the result" @what-is-the-answer-to-life)
(shutdown-agents)


;; You may get something like this
;;
;; [Main] calculate the answer to life the universe and everything
;; [Future] started computation
;; [Main] created future
;; [Main] do other things while waiting for the answer
;; [Main] get the answer
;; [Future] completed computation
;; [Main] the result 42


;; Note: If you leave out the call to (shutdown-agents), the program will on
;; most (all?) OS/JVM combinations "hang" for 1 minute before the process exits.
;; It is simply waiting for background threads, created by the future call, to
;; be shut down.  shutdown-agents will shut them down immediately, or
;; (System/exit <exit-status>) will exit immediately without waiting for them
;; to shut down.

;; This wait occurs even if you use futures indirectly through some other Clojure
;; functions that use them internally, such as pmap or clojure.java.shell/sh

;; http://dev.clojure.org/jira/browse/CLJ-124 is a ticket opened against Clojure,
;; as this 1-minute wait is not considered desirable behavior.