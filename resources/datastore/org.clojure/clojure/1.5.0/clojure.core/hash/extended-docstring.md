hash returns a 32-bit integer hash value for any object.  It is
similar to Java's hashCode, but it is consistent with Clojure = (with
a few exceptions -- see below).  hashCode is consistent with Java's
equals method.

When we say a hash function is consistent with =, it means that for
any two values x1 and x2 where (= x1 x2) is true, (= (hash x1)
(hash x2)) is also true.  This is an important property that allows
hash to be used in the implementation of the hash-set and hash-map
data structures.

hash is consistent with =, except for some BigIntegers, Floats, and
Doubles.  This leads to incorrect behavior if you use them as set
elements or map keys (see example below).  Convert BigIntegers to
BigInt using (bigint x), and floats and doubles to a common type
with (float x) or (double x), to avoid this issue.  This behavior is
by choice: http://dev.clojure.org/jira/browse/CLJ-1036

Examples:

    user=> (def x 8589934588)
    #'user/x
    user=> (= (bigint x) (biginteger x))
    true
    user=> (= (hash (bigint x)) (hash (biginteger x)))
    false         ; hash is not consistent with = for all BigInteger values
    user=> (def s1 (hash-set (bigint x)))
    #'user/s1
    user=> (def s2 (hash-set (biginteger x)))
    #'user/s2
    user=> s1
    #{8589934588N}
    user=> s2
    #{8589934588}     ; s1 and s2 look the same
    user=> (= (first s1) (first s2))
    true              ; their elements are =
    ;; However, the sets are not = because of hash inconsistency.
    user=> (= s1 s2)
    false

    user=> (= (float 1.0e9) (double 1.0e9))
    true
    user=> (= (hash (float 1.0e9)) (hash (double 1.0e9)))
    false       ; hash is not consistent with = for all float/double values

See also: (topic Equality)  (TBD)
