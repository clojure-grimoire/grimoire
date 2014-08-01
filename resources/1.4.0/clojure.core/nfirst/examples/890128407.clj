user=> (nfirst [])
nil 

user=> (nfirst ['(a b c) '(b a c) '(c b a) '(a c b)])
(b c)

user=> (nfirst {:a 1, :b 2, :c 3, :d 4})
(1)

user=> (nfirst #{1 2 3})
java.lang.IllegalArgumentException: Don't know how to create ISeq from: java.lang.Integer (NO_SOURCE_FILE:0)