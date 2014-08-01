user=> (> 1 2)
false
user=> (> 2 1)
true
user=> (> 2 2)
false
user=> (> 6 5 4 3 2)
true
user=> (sort > (vals {:foo 5, :bar 2, :baz 10}))
(10 5 2)