;; A future's calculation is started here and it runs in another thread
user=> (def f (future (Thread/sleep 10000) (println "done") 100))
#'user/f
;;if you wait 10 seconds before dereferencing it you'll see "done"

;; When you dereference it you will block until the result is available.
user=> @f
done
100

;; Dereferencing again will return the already calculated value.
=> @f
100
