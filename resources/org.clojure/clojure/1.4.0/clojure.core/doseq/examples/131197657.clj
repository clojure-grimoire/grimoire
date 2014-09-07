user=> (doseq [[[a b] [c d]] (map list {:1 1 :2 2} {:3 3 :4 4})] 
         (prn (* b d)))
3
8
nil

;; where
user=> (map list {:1 1 :2 2} {:3 3 :4 4})
(([:1 1] [:3 3]) ([:2 2] [:4 4]))