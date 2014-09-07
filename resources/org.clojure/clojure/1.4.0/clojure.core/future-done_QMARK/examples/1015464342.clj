;; beware of cancellation !!!

user=> (def f (future (Thread/sleep 5000) (inc 0)))
#'user/f

user=> (future-cancel f)                           
true

user=> (future-cancelled? f)                       
true

user=> (future-done? f)                            
true

user=> @f                                          
java.util.concurrent.CancellationException (NO_SOURCE_FILE:0)