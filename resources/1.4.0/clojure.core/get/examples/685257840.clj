;; to get an index of the element of a vector, use .indexOf
user=> (def v ["one" "two" "three" "two"])
#'user/v

user=> (.indexOf v "two")
1

user=> (.indexOf v "foo")
-1
