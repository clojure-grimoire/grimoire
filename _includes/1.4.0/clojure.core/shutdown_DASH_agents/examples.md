### Example 0
[permalink](#example-0)

{% highlight clojure linenos %}
{% raw %}
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
4{% endraw %}
{% endhighlight %}


### Example 1
[permalink](#example-1)

{% highlight clojure linenos %}
{% raw %}
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
{% endraw %}
{% endhighlight %}


