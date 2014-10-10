user=> (partition-by odd? [1 1 1 2 2 3 3])
((1 1 1) (2 2) (3 3))

user=> (partition-by even? [1 1 1 2 2 3 3])
((1 1 1) (2 2) (3 3))
