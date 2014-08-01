user=> (vector? [1 2 3])
true
user=> (vector? '(1 2 3))
false
user=> (vector? (vec '(1 2 3)))
true