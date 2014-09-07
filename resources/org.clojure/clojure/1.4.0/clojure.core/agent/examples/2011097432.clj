; Agents provide shared access to mutable state. They allow non-blocking (asynchronous as opposed to synchronous atoms) and independent change of individual locations (unlike coordinated change of multiple locations through refs).

; agent creates one:

user=> (def counter (agent 0))
#'user/counter

; send changes its value:
user=> (send counter inc)

; @ or deref provides a snapshot of the current state:
user=> @counter
1

; agents can reference any data structure:

user=> (def pulp-fiction (agent {}))
#'user/pulp-fiction
user=> (send pulp-fiction assoc :act-one "PROLOGUE")
user=> @pulp-fiction
{:act-one "PROLOGUE"}
user=> (send pulp-fiction assoc :act-two "VINCENT VEGA & MARSELLUS WALLACE'S WIFE")
user=> @pulp-fiction
{:act-two "VINCENT VEGA & MARSELLUS WALLACE'S WIFE", :act-one "PROLOGUE"}

; From http://clojure-examples.appspot.com/clojure.core/agent with permission.