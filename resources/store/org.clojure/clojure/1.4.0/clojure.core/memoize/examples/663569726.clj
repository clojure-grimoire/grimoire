;; First we define a function that presumably have some expensive computation.
user=> (defn myfunc[a] (println "doing some work") (+ a 10))
#'user/myfunc

;; Next we create a memoized version of the function.
user=> (def myfunc-memo (memoize myfunc))
#'user/myfunc-memo


;; The first time we call the function with a particular argument the
;; original function is invoked and the value is returned.  The next
;; time the function is called with the same argument the cached result
;; is returned and the original function is NOT called.

user=> (myfunc-memo 1)
doing some work
11
user=> (myfunc-memo 1)
11
user=> (myfunc-memo 20)
doing some work
30
user=> (myfunc-memo 20)
30
