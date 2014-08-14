user=> (fn? 5)
false
user=> (fn? inc)
true
user=> (fn? (fn []))
true
user=> (fn? #(5))
true