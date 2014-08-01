; Testing for thrown exceptions

; Verifies that the specified exception is thrown
user=> (is (thrown? ArithmeticException (/ 1 0)))
#<ArithmeticException java.lang.ArithmeticException: Divide by zero>

; Verified that the exception is thrown, and that the error message matches the specified regular expression.
user=> (is (thrown-with-msg? ArithmeticException #"Divide by zero"
  #_=>                       (/ 1 0)))
#<ArithmeticException java.lang.ArithmeticException: Divide by zero>
user=> 

