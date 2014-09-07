user=> (butlast [1 2 3])
(1 2)
user=> (butlast (butlast [1 2 3]))
(1)
user=> (butlast (butlast (butlast [1 2 3])))
nil