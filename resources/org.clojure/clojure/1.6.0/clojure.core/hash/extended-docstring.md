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
by choice: [CLJ-1036](http://dev.clojure.org/jira/browse/CLJ-1036)
