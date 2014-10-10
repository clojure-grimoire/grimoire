;; the shortcut form for (fn ) is #( )
;; where parameters are referred by their index with the prefix %

;; the equivalent of 
user=> ((fn [a b c] (+ a b c)) 2 4 6)
12

;; is
user=> (#(+ %1 %2 %3) 2 4 6)
12
