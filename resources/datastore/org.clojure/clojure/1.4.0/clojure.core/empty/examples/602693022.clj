user=> (empty [1 2 3])
[]

user=> (empty (list 1 2 3))
()

user=> (map empty [[\a \b] {1 2} (range 4)])
([] {} ())

user=> (swap! (atom (range 10)) empty)      
()