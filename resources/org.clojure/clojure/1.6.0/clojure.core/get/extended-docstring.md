'get' works for several types of arg 'map', not only maps:

* maps, including records and Java objects implementing java.util.Map
* sets, but not Java objects implementing java.util.Set
* vectors, where the key is the index of the element to get
* strings and Java arrays, where again the key is the index
* nil, for which get always returns not-found or nil

If you want a simpler way to determine whether a key is in a map or an
element is in a set, without having to know a not-found value that is
guaranteed not to be a return value for a key/element in the
collection, use contains?.

The only conditions in which 'get' will throw an exception is
indirectly, e.g. because you called it on a sorted set or map, and the
comparator function throws an exception when comparing two values.  It
will not even throw an exception if you use an out-of-bounds index for
a vector or array:

    user=> (get [:a :b :c] 50)
    nil

Clojure allows you to leave out the 'get' to achieve a more concise
syntax for vectors and maps.  Differences:

* No not-found value vectors.  You may use one for maps.
* `(my-vector idx)` will throw an exception if idx is not an integer,
  or out of the bounds for the vector.
* It does not work for records, strings, or Java Maps or arrays.

Similar to contains?, 'get' has some unusual cases where non-integer
numbers will be rounded off to integers without any errors.

    user=> (get [:a :b :c] 1.7)
    nil     ; on a vector, not found
    user=> (get (int-array [5 6 7]) -0.99)
    5       ; on a Java array, truncate to int, then index is found

Also similar to contains?, 'get' indices are truncated to 32-bit ints,
so some large integers that are out of index bounds for a vector,
string, or array can be truncated to 32-bit ints that are in range
after removing their most significant bits.

    user=> (def long-truncates-to-int-0 (bit-shift-left 1 33))
    user=> (get "abc" long-truncates-to-int-0)
    \a
    user=> (get [:a :b :c] long-truncates-to-int-0)
    :a
