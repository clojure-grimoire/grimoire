user=> (take 5 (repeat "x"))
("x" "x" "x" "x" "x")

;; which is the same as:
user=> (repeat 5 "x")
("x" "x" "x" "x" "x")

;; It should be noted that repeat simply repeats the value n number of times.
;; If you wish to execute a function to calculate the value each time you 
;; probably want the repeatedly function.

