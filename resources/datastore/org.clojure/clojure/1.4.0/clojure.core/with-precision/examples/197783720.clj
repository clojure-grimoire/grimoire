;; The "M" suffix denotes a BigDecimal instance
;; http://download.oracle.com/javase/6/docs/api/java/math/BigDecimal.html

user=> (with-precision 10 (/ 1M 6))
0.1666666667M

user=> (.floatValue 0.1666666667M)
0.16666667
