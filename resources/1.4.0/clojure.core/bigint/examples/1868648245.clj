user=> (bigint 30)
30


;; Actually do something BigInteger-ish... (http://download.oracle.com/javase/6/docs/api/)

user=> (def x (bigint 97))
#'user/x

user=> (.isProbablePrime (.toBigInteger x) 100)
true
