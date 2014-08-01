;; Extract values from a map.

user=> ((juxt :a :b) {:a 1 :b 2 :c 3 :d 4})
[1 2]
