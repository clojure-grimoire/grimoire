;; Create the agent that we will be using
user=> (def a (agent 0))
#'user/a

;; Dereference the agent to show the value is 0
user=> @a
0

;; Create a function that can increment the agent
;; This will continually update the value of the agent
user=> (defn agent-inc [a]
        (send-off *agent* agent-inc)
        (inc a))
#'user/agent-inc

;; Send the agent to the agent-inc function
;; The value is 188 because by the time the repl has sent off the
;; agent to the function, the function has already been called recursively
user=> (send a agent-inc)
#<Agent@6b850d: 188>

;; Dereference of the value a second or so later
user=> @a
716889

;; Another dereference in another couple of seconds
user=> @a
1455264

;; Shutdown the threads for the agents
user=> (shutdown-agents)
nil

;; Dereference the agent to see what value it is
user=> @a
3522353

;; Dereference the agent again in a few seconds
;; It's the same value, because the agent pool of threads are no longer
;; active
user=> @a
3522353
