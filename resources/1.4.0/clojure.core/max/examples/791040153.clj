;; If elements are already in a sequence, use apply
user=> (apply max [1 2 3 4 3])
4
user=> (apply max '(4 3 5 6 2))
6