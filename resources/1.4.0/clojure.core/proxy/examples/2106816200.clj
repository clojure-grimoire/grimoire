;; BUG: proxy dispatches *only* on name, not arity:
user=> (let [p (proxy [java.io.InputStream] [] (read [] -1))]
         (println (.read p))
         (println (.read p (byte-array 3) 0 3)))

-1
ArityException Wrong number of args (4) passed to: core$eval213$fn  clojure.lang.AFn.throwArity (AFn.java:437)
