;; Can be used to test set membership
user=> (def s #{"a" "b" "c"})

user=> (contains? s "a")
true

user=> (contains? s "z")
false