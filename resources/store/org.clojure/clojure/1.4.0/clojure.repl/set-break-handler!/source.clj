(defn set-break-handler!
  "Register INT signal handler.  After calling this, Ctrl-C will cause
  the given function f to be called with a single argument, the signal.
  Uses thread-stopper if no function given."
  ([] (set-break-handler! (thread-stopper)))
  ([f]
   (sun.misc.Signal/handle
     (sun.misc.Signal. "INT")
     (proxy [sun.misc.SignalHandler] []
       (handle [signal]
         (f (str "-- caught signal " signal)))))))