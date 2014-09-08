;; It is useful to combine macroexpand with pprint as the
;; default output can be hard to read.
user=> (clojure.pprint/pprint (macroexpand '(time (print "timing"))))
(let*
 [start__3917__auto__
  (. java.lang.System (clojure.core/nanoTime))
  ret__3918__auto__
  (print "timing")]
 (clojure.core/prn
  (clojure.core/str
   "Elapsed time: "
   (clojure.core//
    (clojure.core/double
     (clojure.core/-
      (. java.lang.System (clojure.core/nanoTime))
      start__3917__auto__))
    1000000.0)
   " msecs"))
 ret__3918__auto__)

;; Even after pretty printing you may benefit from some
;; manual cleanup.

;; Also worth noting that most of the time macroexpand-1 is
;; a better alternative to avoid over expanding down too 
;; many levels.