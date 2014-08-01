user=> (count nil)
0

user=> (count [])
0

user=> (count [1 2 3])
3

user=> (count {:one 1 :two 2})
2

user=> (count [1 \a "string" [1 2] {:foo :bar}])
5

user=> (count "string")
6