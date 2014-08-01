user=> (apply-template '[a b c d e] '[d a b e c e b a d] '(1 2 3 4 5))
[4 1 2 5 3 5 2 1 4]  

user=> (apply-template '[a b c d e] '[d a b e c e b a d] '(1 [2 3] [4 5]))
[d 1 [2 3] e [4 5] e [2 3] 1 d]