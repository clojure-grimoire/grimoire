user=> (num 2048)
2048


;; Calling a Number http://download.oracle.com/javase/6/docs/api/ method:

user=> (def x (num 2048))
#'user/x

user=> (.floatValue x)
2048.0
