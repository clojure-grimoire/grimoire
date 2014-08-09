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
