user=> (contains? #{:a :b 5 nil} :b)       ; :b is in the set
true
user=> (contains? #{:a :b 5 nil} 2)        ; 2 is not
false
user=> (contains? #{:a :b 5 nil} nil)      ; nil is in this set
true
user=> (contains? #{:a :b 5} nil)          ; but not in this one
false
