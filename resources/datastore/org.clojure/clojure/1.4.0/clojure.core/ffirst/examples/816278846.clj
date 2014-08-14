user=> (ffirst '([]))
nil 

user=> (ffirst ['(a b c) '(b a c)])
a 

user=> (ffirst '([a b c] [b a c]))
a