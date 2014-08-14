user=> (map first (partition-by identity [1 1 2 3 3 1 1 5 5]))
(1 2 3 1 5)