user=> (def store (ref {}))
#'user/store
user=> (ref-history-count store)
0
user=> (ref-max-history store)
10
user=> (ref-min-history store)
0