(map fn [a 4 x]
        [b 5 y]
        [c 6])    
;        ^ ^
; applies fn to a b c as (fn a b c)
; applies fn to 4 5 6 as (fn 4 5 6)
; ignores (x y)
; returns a list of results
; equivalent to (list (fn a b c) (fn 4 5 6))

;example
(map list [1 2 3]
         '(a b c)
         '(4 5))

user=> (map list  [1 2 3] '(a b c) '(4 5))
((1 a 4) (2 b 5))
;same as
user=> (list (list 1 'a 4) (list 2 'b 5))
((1 a 4) (2 b 5))