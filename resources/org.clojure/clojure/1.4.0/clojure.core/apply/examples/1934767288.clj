;apply is used to apply an operator to its operands. 

(apply + '(1 2))  ; equal to (+ 1 2)
=> 3


;you can also put operands before the list of operands and they'll be consumed in the list of operands

(apply + 1 2 '(3 4))  ; equal to (apply + '(1 2 3 4))
=> 10