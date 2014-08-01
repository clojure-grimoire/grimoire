(= x y) is true if x and y are both:

* numbers in the same 'category', and numerically the same, where
  category is one of (integer or ratio), floating point, or
  BigDecimal.  Use == if you want to compare for numerical equality
  between different categories, or you want an exception thrown if
  either value is not a number.
* sequences, lists, vectors, or queues, with equal elements in the
  same order.
* sets, with equal elements, ignoring order.
* maps, with equal key/value pairs, ignoring order.
* symbols, or both keywords, with equal namespaces and names.
* refs, vars, or atoms, and they are the same object, i.e. (identical?
  x y) is true.
* the same type defined with deftype.  The type's equiv method is
  called and its return value becomes the value of (= x y).
* other types, and Java's x.equals(y) is true.  The result should be
  unsurprising for nil, booleans, characters, and strings.

If you call = with more than two arguments, the result will be true
when all consecutive pairs are =.

Exceptions, or possible surprises:

* When comparing collections with =, numbers within the collections
  are also compared with =, so the three numeric categories above
  are significant.
* = is false for BigDecimal values with different scales, e.g. (=
  1.50M 1.500M) is false.  http://dev.clojure.org/jira/browse/CLJ-1118
* 'Not a Number' values Float/NaN and Double/NaN are not equal to any
  value, not even themselves.  This leads to odd behavior if you use
  them as set elements or map keys.

Examples:

    user=> (= 3 3N)   ; same category integer
    true
    user=> (= 2 2.0)  ; different categories integer and floating point
    false
    user=> (= [0 1 2] '(0 1 2))
    true
    user=> (= '(0 1 2) '(0 1 2.0))   ; 2 and 2.0 are not =
    false

    ;; While this map is similar to the vector in that it maps the
    ;; same integers 0, 1, and 2 to the same values, maps and vectors
    ;; are never = to each other.
    user=> (= ["a" "b" "c"] {0 "a" 1 "b" 2 "c"})
    false

    user=> (= (with-meta #{1 2 3} {:key1 1}) (with-meta #{1 2 3} {:key1 2}))
    true                  ; Metadata is ignored when comparing

    user=> (= Double/NaN Double/NaN)  ; this is normal
    false

    user=> (def s1 #{1.0 2.0 Double/NaN})
    #'user/s1
    user=> s1
    #{2.0 1.0 NaN}
    user=> (contains? s1 1.0)         ; this is expected
    true
    user=> (contains? s1 Double/NaN)  ; this might surprise you
    false
