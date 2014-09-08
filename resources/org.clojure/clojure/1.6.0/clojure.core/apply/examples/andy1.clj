user=> (apply + [1 2])           ; same as (+ 1 2)
3
user=> (apply + 1 2 [3 4 5 6])   ; same as (+ 1 2 3 4 5 6)
21
user=> (apply + [])              ; same as (+)
0
