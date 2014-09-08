
user=> (def f (future (inc 0)))
#'user/f

user=> (future-cancel f)                           
false

user=> (future-cancelled? f)                       
false