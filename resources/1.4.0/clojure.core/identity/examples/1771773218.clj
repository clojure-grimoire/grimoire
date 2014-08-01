user=> (map #(%1 %2) (cycle [inc identity]) [1 2 3 4 5 6 7 8 9 10])
(2 2 4 4 6 6 8 8 10 10)
