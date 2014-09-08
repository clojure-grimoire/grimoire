(defn print-stack-trace
  "Prints a Clojure-oriented stack trace of tr, a Throwable.
  Prints a maximum of n stack frames (default: unlimited).
  Does not print chained exceptions (causes)."
  {:added "1.1"}
  ([tr] (print-stack-trace tr nil))
  ([^Throwable tr n]
     (let [st (.getStackTrace tr)]
       (print-throwable tr)
       (newline)
       (print " at ") 
       (if-let [e (first st)]
         (print-trace-element e)
         (print "[empty stack trace]"))
       (newline)
       (doseq [e (if (nil? n)
		   (rest st)
		   (take (dec n) (rest st)))]
	 (print "    ")
	 (print-trace-element e)
	 (newline)))))