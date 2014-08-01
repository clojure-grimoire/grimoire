user=> (letfn [(twice [x]
                 (* x 2))
               (six-times [y]
                 (* (twice y) 3))]
         (println "Twice 15 =" (twice 15))
         (println "Six times 15 =" (six-times 15)))
Twice 15 = 30
Six times 15 = 90
nil

;; Unable to resolve symbol: twice in this context
user=> (twice 4)
; Evaluation aborted.

;; Unable to resolve symbol: six-times in this context
user=> (six-times 100)
; Evaluation aborted.
