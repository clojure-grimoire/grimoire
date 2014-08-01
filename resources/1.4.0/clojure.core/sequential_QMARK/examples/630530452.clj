user=> (sequential? '(1 2 3))
true

user=> (sequential? [1 2 3])
true

user => (sequential? (range 1 5))
true

user=> (sequential? 1)
false

user => (sequential? {:a 2 :b 1})
false
