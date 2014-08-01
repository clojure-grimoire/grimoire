
user=> (concat [1 2] [3 4])
(1 2 3 4)

user=> (into [] (concat [1 2] [3 4]))
[1 2 3 4]

user=> (concat [:a :b] nil [1 [2 3] 4])
(:a :b 1 [2 3] 4)

=> (concat [1] [2] '(3 4) [5 6 7] #{9 10 8})
(1 2 3 4 5 6 7 8 9 10)
