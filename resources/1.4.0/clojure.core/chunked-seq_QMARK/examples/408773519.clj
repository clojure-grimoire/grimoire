user=> (chunked-seq? (range 1000))
false

user=> (chunked-seq? (seq (range 1000)))
true

user=> (chunked-seq? (iterate inc 10))
false

user=> (chunked-seq? (seq (iterate inc 10)))
false