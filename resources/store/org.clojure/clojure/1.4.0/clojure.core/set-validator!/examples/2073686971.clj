user=> (def atm (atom [2])
#'user/atm

user=> (set-validator! atm #(every? even? %))
nil

user=> (swap! atm into [5])
#<CompilerException java.lang.IllegalStateException: Invalid reference state (NO_SOURCE_FILE:0)>

user=> (set-validator! atm nil)
nil

user=> (swap! atm into [5]))
[2 5]