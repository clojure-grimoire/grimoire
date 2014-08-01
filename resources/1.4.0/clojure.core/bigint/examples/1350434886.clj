user> (reduce * (repeat 20 1000))
ArithmeticException integer overflow  clojure.lang.Numbers.throwIntOverflow (Numbers.java:1388)

user> (reduce * (repeat 20 (bigint 1000)))
1000000000000000000000000000000000000000000000000000000000000N
