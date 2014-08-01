v.1.3.0
user=> (= 21 (long 21))
true 

;; but
user=> (.equals 21 (long 21))
false 

;; and thus
user=> (get {21 :twenty-one} (long 21))
nil 

v.1.6.0
user=> (= 21 (long 21))
true 

user=> (.equals 21 (long 21))
true

user=> (.equals 21.0 (long 21))
false

user=> (.equals (long 21.0) (long 21)) 
true