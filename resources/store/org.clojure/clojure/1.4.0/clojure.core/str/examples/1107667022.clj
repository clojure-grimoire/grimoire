user=> "some string"
"some string"

user=> (str)
""
user=> (str nil)
""
user=> (str 1)
"1"
user=> (str 1 2 3)
"123"
user=> (str 1 'symbol :keyword)
"1symbol:keyword"

;; A very common usage of str is to apply it to an existing collection:
user=> (apply str [1 2 3])
"123"

;; compare it with:
user=> (str [1 2 3])
"[1 2 3]"

