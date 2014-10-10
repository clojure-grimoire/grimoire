user=> `(1 2 ~(list 3 4))

(1 2 (3 4))

user=> `(1 2 ~@(list 3 4))

(1 2 3 4)

; borrowed from StackOverflow: 
; http://stackoverflow.com/questions/4571042/can-someone-explain-clojures-unquote-splice-in-simple-terms