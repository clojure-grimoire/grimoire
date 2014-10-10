
user=> (def f (future (Thread/sleep 5000) (inc 0)))
#'user/f

user=> (future-cancel f)                           
true

user=> (future-cancelled? f)                       
true