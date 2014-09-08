user=> (def boring (constantly 10))
#'user/boring

user=> (boring 1 2 3)
10
user=> (boring)
10
user=> (boring "Is anybody home?")
10
