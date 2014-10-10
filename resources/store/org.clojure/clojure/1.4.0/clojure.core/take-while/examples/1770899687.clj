user=> (take-while neg? [-2 -1 0 1 2 3])
(-2 -1)

user=> (take-while neg? [-2 -1 0 -1 -2 3])
(-2 -1)

user=> (take-while neg? [ 0 1 2 3])
()

user=> (take-while neg? [])
()

user=> (take-while neg? nil)
()