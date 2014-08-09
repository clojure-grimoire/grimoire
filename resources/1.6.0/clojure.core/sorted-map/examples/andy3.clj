;; you may search an unsorted map for any value with no exception.

user=> (m1 1)
"intone"
user=> (m1 "a")
nil     ; no exception, just nil indicating no such key "a"
