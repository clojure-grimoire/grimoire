; Combine a set of collections into a single collection
user=> (reduce into [[1 2 3] [:a :b :c] '([4 5] 6)])
[1 2 3 :a :b :c [4 5] 6]