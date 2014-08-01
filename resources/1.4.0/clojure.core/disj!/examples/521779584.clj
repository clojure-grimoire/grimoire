;; Note how we always use the return value of disj! and conj! in these examples
;; for all future modifications, rather than (incorrectly) ignoring the return
;; value and continuing to modify the original transient set.  See examples for
;; assoc! and dissoc! for more discussion and examples of this.

user=> (def foo (transient #{'pore-pore 'slow 'yukkuri}))
#'user/foo
user=> (count foo)
3
user=> (def foo (disj! foo 'yukkuri))
#'user/foo
user=> foo
#<TransientHashSet clojure.lang.PersistentHashSet$TransientHashSet@3bd840d9>
user=> (count foo)
2
user=> (def foo (conj! foo 'yukkuri))
#'user/foo
user=> foo
#<TransientHashSet clojure.lang.PersistentHashSet$TransientHashSet@3bd840d9>
user=> (count foo)
3
user=> (def foo (persistent! foo))
#'user/foo
user=> foo
#{yukkuri slow pore-pore}
