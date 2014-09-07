user=> (drop-last [1 2 3 4])
(1 2 3) 

user=> (drop-last -1 [1 2 3 4])
(1 2 3 4) 

user=> (drop-last 0 [1 2 3 4])
(1 2 3 4) 

user=> (drop-last 2 [1 2 3 4])
(1 2)

user=> (drop-last 5 [1 2 3 4])
()