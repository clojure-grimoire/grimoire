;; do is used to evaluate multiple expressions in order, usually for the
;; purpose of evaluating exprs that have side-effects (such as printing
;; or I/O).  do returns the value of its last expression.
;;
;; do w/o args returns nil.

=> (do
     (println "LOG: Computing...")
     (+ 1 1))
LOG: Computing...
2

=> (do)
nil
