user=> (spit "event.log" "test 1\n" :append true)
nil

user=> (spit "event.log" "test 2\n" :append true)
nil

user=> (println (slurp "event.log"))
test 1
test 2

nil
