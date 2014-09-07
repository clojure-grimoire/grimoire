user=> (doseq [[x y] (map list [1 2 3] [1 2 3])] 
         (prn (* x y)))
1
4
9
nil

;; where
user=> (map list [1 2 3] [1 2 3])
((1 1) (2 2) (3 3))