user> (def foo (transient [1 2 3]))
#'user/foo
user> foo
#<TransientVector clojure.lang.PersistentVector$TransientVector@12c9b4d1>
user> (persistent! foo)
[1 2 3]
user> foo
#<TransientVector clojure.lang.PersistentVector$TransientVector@12c9b4d1>
user> (conj! foo 4)
â†’ ERROR:Transient used after persistent! call
user> (persistent! foo)
â†’ ERROR: Transient used after persistent! call