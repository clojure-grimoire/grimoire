user=> (fnext ['(a b c) '(b a c)])
(b a c) 

user=> (fnext '([a b c] [b a c]))
[b a c] 

user=> (fnext {:a 1 :b 2 :c 3})
[:b 2] 

user=> (fnext [])
nil 

user=> (fnext [1])
nil