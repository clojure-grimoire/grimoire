;; demonstrate the the unchecked-multiply function cannot calculate
;; 1 trillion * 10 without throwing an exception

user=> (def thous 1000)
#'user/thous
user=> (def trill (* thous thous thous thous))
#'user/trill
user=> (unchecked-multiply trill 10)
java.lang.IllegalArgumentException: No matching method found: unchecked_multiply
 (NO_SOURCE_FILE:0)
user=> (* trill 10)
10000000000000
user=>