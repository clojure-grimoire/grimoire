user=> (def v (delay (do (println "start sleeping") 
                         (Thread/sleep 1000) 
                         10)))
#'user/v
user=> (delay? v)
true
user=> (force v)
start sleeping
10
user=> (delay? v)
true
user=> (force v)
10
user=> 