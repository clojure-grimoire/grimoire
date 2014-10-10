(defmacro print-length-loop
  "A version of loop that iterates at most *print-length* times. This is designed 
for use in pretty-printer dispatch functions."
  {:added "1.3"}
  [bindings & body]
  (let [count-var (gensym "length-count")
        mod-body (pll-mod-body count-var body)]
    `(loop ~(apply vector count-var 0 bindings)
       (if (or (not *print-length*) (< ~count-var *print-length*))
         (do ~@mod-body)
         (.write ^java.io.Writer *out* "...")))))