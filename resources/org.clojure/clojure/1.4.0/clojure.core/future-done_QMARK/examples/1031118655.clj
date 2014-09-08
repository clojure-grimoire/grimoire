user=> (def f (future (Thread/sleep 5000) (inc 0)))

user=> (future-done? f)                            
false

user=> (Thread/sleep 5000)
nil

user=> (future-done? f)
true

