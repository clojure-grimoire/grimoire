(def bond (agent 7))

(defn err-handler-fn [ag ex]
  (println "evil error occured: " ex " and we still have value " @ag))

(set-error-handler! bond err-handler-fn)

;;division by zero:

(send bond (fn [x] (/ x 0)))
=>evil error occured:  #<ArithmeticException java.lang.ArithmeticException: 
=>Divide by zero>  and we still have value  7

(send bond inc)
=>FAILURE ;;Agent is failed, needs restart, but keeps the last OK value

@bond
=>7

(restart-agent bond 7) ;; or replace 7 with @ag

(send bond inc)
=>#<Agent@88d00c6: 7> ;;because of async update

@bond
=>8
