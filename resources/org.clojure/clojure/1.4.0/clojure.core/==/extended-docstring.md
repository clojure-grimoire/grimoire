(== x y) is true if x and y are both numbers, and represent
numerically equal values.  Unlike =, there are no separate
'categories' of numeric values that are treated as always unequal to
each other.  If you call == with more than two arguments, the result
will be true when all consecutive pairs are ==.  An exception is
thrown if any argument is not a numeric value.

Exceptions, or possible surprises:

* == is false for BigDecimal values with different scales, e.g. (==
  1.50M 1.500M) is false.  http://dev.clojure.org/jira/browse/CLJ-1118
* 'Not a Number' values Float/NaN and Double/NaN are not equal to any
  value, not even themselves.

Examples:

    user=> (= 2 2.0)   ; = has different categories integer and floating point
    false
    user=> (== 2 2.0)  ; but == sees same numeric value
    true
    user=> (== 5 5N (float 5.0) (double 5.0) (biginteger 5))
    true
    user=> (== 5 5.0M) ; this is likely a bug
    false
    user=> (== Double/NaN Double/NaN)  ; this is normal
    false
    user=> (== 2 "a")
    ClassCastException java.lang.String cannot be cast to java.lang.Number  clojure.lang.Numbers.equiv (Numbers.java:206)
