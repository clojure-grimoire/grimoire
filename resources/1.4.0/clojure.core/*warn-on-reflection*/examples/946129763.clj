;; define two variables then set *warn-on-reflection* to true and try to call
;; one of their Java methods. Warnings are generated in both cases
;; set *warn-on-reflection* to false and note that you can call both functions
;; without a warning

user=> (def i 23)
#'user/i
user=> (def s "123")
#'user/s
user=> (set! *warn-on-reflection* true)
true
user=> (.toString i)
Reflection warning, NO_SOURCE_PATH:4 - reference to field toString can't be resolved.
"23"
user=> (.toString s)
Reflection warning, NO_SOURCE_PATH:5 - reference to field toString can't be resolved.
"123"
user=> (set! *warn-on-reflection* false)
false
user=> (.toString i)
"23"
user=> (.toString s)
"123"
user=>