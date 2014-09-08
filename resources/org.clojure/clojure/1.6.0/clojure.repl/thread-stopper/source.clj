(defn thread-stopper
  "Returns a function that takes one arg and uses that as an exception message
  to stop the given thread.  Defaults to the current thread"
  ([] (thread-stopper (Thread/currentThread)))
  ([thread] (fn [msg] (.stop thread (Error. msg)))))