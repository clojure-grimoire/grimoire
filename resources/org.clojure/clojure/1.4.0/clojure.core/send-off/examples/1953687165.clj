user=> (def my-agent (agent ""))
#'user/my-agent
user=> @my-agent
""

;; Note the following happens asynchronously in a thread
;; pool
user=> (send my-agent #(slurp %2) "file.txt")
#<Agent@13c6641: "">

;; Assuming the action has been invoked the value will
;; now be updated when we look at it.
user=> @my-agent
"file contents"

