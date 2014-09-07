;; Creating an agent
user> (def a (agent 1))
#'user/a

;; Create a function that can handle an agent

user> (defn agent-action [a]
	33)
#'user/agent-action

;; The agent will become 33
user> (send-off a agent-action)
#<Agent@dde4f2: 33>

user> @a
33
;; Create another agent before shutdown
user> (def c (agent 3))
#'user/c

;; Shutdown agents is called
user> (shutdown-agents)
nil

;; Attempt to turn c into 33
user> (send c agent-action)
#<Agent@b162fa: 3>

;; The result is that it is still the same value it was initialized with
user> @c
3

;; Agent created after shutdown
user> (def d (agent 4))
#'user/d

;; Try sending it
user> (send d agent-action)
#<Agent@356519: 4>

;; Same thing, there are no threads to process the agents
user> @d
4