;; Oops! Don't this!!!
user=> (iterate inc 0)
;; Frantically doing C-c C-c :-P
; Evaluation aborted.

user=> (set! *print-length* 10)
10

;; Now it's perfectly fine. Yay!
user=> (iterate inc 0)
(0 1 2 3 4 5 6 7 8 9 ...)

