;; for fast interop
user=> (set! *warn-on-reflection* true)
true
user=> (defn get-a-bool [bs] (aget bs 1))
Reflection warning, NO_SOURCE_PATH:1 - call to aget can't be resolved.
#'user/get-a-bool
user=> (defn get-a-bool [bs] (let [bs (booleans bs)] (aget bs 1)))
#'user/get-a-bool
