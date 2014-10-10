user=> (zero? 0)
true
user=> (zero? 0.0)
true
user=> (zero? 1)
false
user=> (zero? 0x0)
true
user=> (zero? 3.14159265358M)
false
user=> (zero? (/ 1 2))
false