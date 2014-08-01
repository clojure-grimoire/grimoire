;; Note how we always use the return value of pop! in these examples
;; for all future modifications, rather than (incorrectly) ignoring the return
;; value and continuing to modify the original transient set.  See examples for
;; assoc! and dissoc! for more discussion and examples of this.

user=> (def foo (transient [1 2 3]))
#'user/foo
user=> (count foo)
3
user=> (def foo (pop! foo))
#'user/foo
user=> foo
#<TransientVector clojure.lang.PersistentVector$TransientVector@1638fff7>
user=> (count foo)
2
user=> (def foo (pop! foo))
#'user/foo
user=> (count foo)
1
user=> (def foo (persistent! foo))
#'user/foo
user=> (count foo)
1
user=> foo
[1]
