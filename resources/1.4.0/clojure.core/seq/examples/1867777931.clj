;(seq x) is the recommended idiom for testing if a collection is not empty
user=> (every? seq ["1" [1] '(1) {:1 1} #{1}])
true