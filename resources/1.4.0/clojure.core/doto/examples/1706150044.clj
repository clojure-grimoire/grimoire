;; Note that even though println returns nil, doto still returns the HashMap object
user> (doto (java.util.HashMap.)
            (.put "a" 1)
            (.put "b" 2)
            (println))
#<HashMap {b=2, a=1}>
{"b" 2, "a" 1}
