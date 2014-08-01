user=> (number? 1)
true
user=> (number? 1.0)
true
user=> (number? :a)
false
user=> (number? nil)
false
user=> (number? "23")
false