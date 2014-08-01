user=> (ref [])
#<ref@5fafa486: []>

user=> (ref 1 :validator pos?)
#<Ref@6c484c6b: 1>

=> (ref 0 :validator pos?)
IllegalStateException Invalid reference state  clojure.lang.ARef.validate (ARef.java:33)

=> (dosync (ref-set (ref 1 :validator pos?) 0))
IllegalStateException Invalid reference state  clojure.lang.ARef.validate (ARef.java:33)

=> (dosync (ref-set (ref 1 :validator pos?) 2))
2