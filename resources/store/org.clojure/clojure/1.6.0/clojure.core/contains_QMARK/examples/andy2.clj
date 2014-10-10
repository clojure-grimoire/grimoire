user=> (contains? {:a "a" nil "nil"} :a)   ; key :a is in the map
true
user=> (contains? {:a "a" nil "nil"} :b)   ; :b is not
false
user=> (contains? {:a "a" nil "nil"} nil)  ; nil is a key here
true
user=> (contains? {:a "a"} nil)            ; but not here
false
