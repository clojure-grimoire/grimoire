user=> (unchecked-negate-int 4)
-4
user=> (unchecked-negate-int 0)
0
user=> (unchecked-negate-int -7)
7
user=> (unchecked-negate-int Integer/MAX_VALUE)
-2147483647
user=> (unchecked-negate-int Integer/MIN_VALUE) ;overflow
-2147483648