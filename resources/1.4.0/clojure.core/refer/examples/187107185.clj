;;; `:only' accepts only original names.
;; wrong
user=> (refer 'clojure.string
              :rename '{capitalize cap, trim trm}
              :only '[cap trm])
IllegalAccessError cap does not exist  clojure.core/refer (core.clj:3849)

;; right
user=> (refer 'clojure.string
              :rename '{capitalize cap, trim trm}
              :only '[capitalize trim])
nil

;; work well
user=> (cap (trm " hOnduRAS  "))
"Honduras"

;; and also, cannot use either of them.
user=> (join \, [1 2 3])
CompilerException java.lang.RuntimeException: Unable to resolve symbol: join in this context, compiling:(NO_SOURCE_PATH:1:1)