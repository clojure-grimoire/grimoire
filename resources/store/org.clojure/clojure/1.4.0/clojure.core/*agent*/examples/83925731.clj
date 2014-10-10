;; *agent* is often used with send or send-off to set up a repeated
;; transformation of an agent's value. For example, to repeatedly 
;; increment the integer value of an agent 'myagent' until some 
;; flag value 'running' evaluates to false:

;; Create an agent set to an initial value of 0:
(def myagent (agent 0))

;; Define a variable to act as a boolean flag:
(def running true)

;; Define a function to increment agent value repeatedly:
(defn inc-while-running [agent-value]
  (when running
    (send-off *agent* inc-while-running)) ;sets up another call
  (inc agent-value))

;; Dereference myagent to confirm it is set to value 0:
user=> @myagent
0

;; Start the fun:
user=> (send-off myagent inc-while-running)
#&lt;Agent@5fb9f88b: 20&gt;

;; The agent has already been incremented many times (20 when I ran this)
;; by the time the REPL prints.

;; Redefine running as false to stop repeated send-off:
(def running false)

;; Dereference myagent to find its new value:
user=> @myagent
848167

;; Dereference again to make sure incrementation has stopped:
user=> @myagent
848167