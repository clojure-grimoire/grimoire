compare is the default comparator for sorting with sort and sort-by,
for ordering the elements of a sorted-set, and for ordering the keys
of a sorted-map.  See (topic Comparators).

As for all 3-way comparators, it takes two arguments x and y.  It
returns an int that is negative if x should come before y, positive if
x should come after y, or 0 if they are equal.

compare works for many types of values, ordering values as follows:

* numbers: increasing numeric order, returning 0 if two numbers are
  numerically equal by ==, even if = returns false
* strings, symbols, keywords: lexicographic order (aka dictionary
  order) by their representation as sequences of UTF-16 code units.
  This is alphabetical order (case-sensitive) for strings restricted
  to the ASCII subset.
* vectors: shortest-to-longest, with lexicographic ordering among
  equal length vectors.
* All Java types implementing the Comparable interface such as
  characters, booleans, File, URI, and UUID are compared via their
  compareTo methods.
* nil: can be compared to all values above, and is considered less
  than anything else.

compare throws an exception if given two values whose types are "too
different", e.g. it can compare Integers, Longs, and Doubles to each
other, but not strings to keywords or keywords to symbols.  It cannot
compare lists, sequences, sets, or maps.

Examples:

    user=> (sort [22/7 2.71828 Double/NEGATIVE_INFINITY 1 55 3N])
    (-Infinity 1 2.71828 3N 22/7 55)

    user=> (def sset1 (sorted-set "aardvark" "boo" "a"
                                  "Antelope" "bar"))
    #'user/sset1
    user=> sset1
    #{"Antelope" "a" "aardvark" "bar" "boo"}

See Java documentation of String's compareTo method for additional
details on String comparison.

Symbols are sorted by their representation as strings, sorting first
by their namespace name, and if they are in the same namespace, then
by their name.  If no namespace is included, those symbols will be
sorted before any symbol with a namespace.  Keywords are sorted
similarly to symbols.

    user=> (def sset2 (sorted-set 'user/foo 'clojure.core/pprint 'bar
                                  'clojure.core/apply 'user/zz))
    #'user/sset2
    user=> sset2
    #{bar clojure.core/apply clojure.core/pprint user/foo user/zz}

    user=> (def smap1 (sorted-map :map-key 10, :amp [3 2 1],
                                  :blammo "kaboom"))
    #'user/smap1
    user=> smap1
    {:amp [3 2 1], :blammo "kaboom", :map-key 10}

Vectors are sorted by their length first, from shortest to longest,
then lexicographically among equal-length vectors.

    user=> (sort [[1 2] [1 -5] [10000] [4 -1 20] [3 2 5]])
    ([10000] [1 -5] [1 2] [3 2 5] [4 -1 20])

An exception will be thrown if you call compare with different
types (any numeric types above can be compared to each other, but not
to a non-numeric type).  An exception will also be thrown if you use
compare on a list, set, map, or any other type not mentioned above.
You must implement your own comparator if you wish to sort such
values.  See [Comparators in Clojure][Comparators] for examples of
comparators that can do this.

    user=> (sort [5 "a"])
    ClassCastException java.lang.Long cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)
    user=> (sort [:foo 'bar])
    ClassCastException clojure.lang.Keyword cannot be cast to clojure.lang.Symbol  clojure.lang.Symbol.compareTo (Symbol.java:106)

    user=> (sort [#{1 2} {2 4}])
    ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)
    user=> (sort [{:a 1 :b 3} {:c -2 :d 4}])
    ClassCastException clojure.lang.PersistentArrayMap cannot be cast to java.lang.Comparable  clojure.lang.Util.compare (Util.java:153)

Implementation detail: Clojure Refs can also be sorted using
compare.  They are sorted in the order they were created.
