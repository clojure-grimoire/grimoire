user=> (= 3 3N)   ; same category integer
true
user=> (= 2 2.0)  ; different categories integer and floating point
false
user=> (= [0 1 2] '(0 1 2))
true
user=> (= '(0 1 2) '(0 1 2.0))   ; 2 and 2.0 are not =
false
