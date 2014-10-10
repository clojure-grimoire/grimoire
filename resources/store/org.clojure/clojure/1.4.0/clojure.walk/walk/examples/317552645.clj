(use 'clojure.walk)

user=> (walk #(* 2 %) #(apply + %) [1 2 3 4 5])
30

user=> (walk second #(apply max %) [ [1 2] [3 4] [5 6] ])
6

user=> (walk first #(apply max %) [ [1 2] [3 4] [5 6] ])
5

user=> (walk first reverse [ [1 2] [3 4] [5 6] ])
(5 3 1)