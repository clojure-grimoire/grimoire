;; If elements are already in a sequence, use apply
user=> (apply min [1 2 3 4 3])
1
user=> (apply min '(4 3 5 6 2))
2