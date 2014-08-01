user=> (last [1 2 3 4 5])
5
user=> (last ["a" "b" "c" "d" "e"])
"e"
user=> (last {:one 1 :two 2 :three 3})
[:three 3]
user=> (last [])
nil
