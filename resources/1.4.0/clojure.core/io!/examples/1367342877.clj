user=> (def a (ref 0))
#'user/a

user=> (dosync
         (io! (println "hello"))
         (alter a inc))
IllegalStateException I/O in transaction

user=> (dosync
         (println "hello")
         (alter a inc))
"hello"
1

user=> (defn fn-with-io []
         (io! (println "hello")))
#'user/fn-with-io

user=> (dosync
         (fn-with-io)
         (alter a inc))
IllegalStateException I/O in transaction