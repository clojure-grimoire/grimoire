Sorted maps maintain their keys in sorted order, sorted by the
function compare.  Use sorted-map-by to get a different key order.

    ;; function compare sorts keywords alphabetically
    user=> (sorted-map :d 0 :b -5 :a 1)
    {:a 1, :b -5, :d 0}
    user=> (assoc (sorted-map :d 0 :b -5 :a 1) :c 57)
    {:a 1, :b -5, :c 57, :d 0}

Sorted maps are in most ways similar to unsorted maps.  Differences
include:

* seq returns a sequence of the key/value pairs in order, sorted by
  their keys.  This affects all other sequence-based operations upon
  sorted maps, e.g. first, rest, map, for, doseq, and many others.
* rseq returns this same sequence but in reverse order.  It does so
  lazily, unlike (reverse (seq coll)), which must generate the entire
  sequence before it can reverse it.
* You can use subseq or rsubseq on a sorted map to get a sorted
  sequence of all key/value pairs with keys in a specified range.
* Unsorted maps use = to compare keys, but sorted maps use compare or
  a caller-supplied comparator.  A sorted map's comparator can throw
  exceptions if you put incomparable keys in the same map.
* There is no transient version of sorted maps.

Examples:

    user=> (def births
             (sorted-map -428 "Plato"      -384 "Aristotle" -469 "Socrates"
                         -320 "Euclid"     -310 "Aristarchus" 90 "Ptolemy"
                         -570 "Pythagoras" -624 "Thales"    -410 "Eudoxus"))
    #'user/births
    user=> (first births)
    [-624 "Thales"]
    user=> (take 4 births)
    ([-624 "Thales"] [-570 "Pythagoras"] [-469 "Socrates"] [-428 "Plato"])
    user=> (keys births)
    (-624 -570 -469 -428 -410 -384 -320 -310 90)
    user=> (vals births)   ; returns values in order by sorted keys
    ("Thales" "Pythagoras" "Socrates" "Plato" "Eudoxus" "Aristotle" "Euclid" "Aristarchus" "Ptolemy")

subseq and rsubseq return a sequence of all key/value pairs with a
specified range of keys.  It takes O(log N) to find the first pair,
where N is the size of the whole map, and O(1) time for each
additional pair, so it is more efficient than the O(N) approach of
taking the entire sequence and filtering out the unwanted pairs.

    user=> (subseq births > -400)
    ([-384 "Aristotle"] [-320 "Euclid"] [-310 "Aristarchus"] [90 "Ptolemy"])
    user=> (subseq births > -400 < -100)
    ([-384 "Aristotle"] [-320 "Euclid"] [-310 "Aristarchus"])
    user=> (rsubseq births > -400 < -100)
    ([-310 "Aristarchus"] [-320 "Euclid"] [-384 "Aristotle"])

Both unsorted and sorted maps follow the rule of 'first equal key to
be added wins'.  The difference is in what keys they consider to be
equal: unsorted uses =, sorted uses compare or a custom comparator.

    user=> (def m1 (hash-map 1.0 "floatone" 1 "intone" 1.0M "bigdecone"
                             1.5M "bigdec1.5" 3/2 "ratio1.5"))
    #'user/m1
    user=> m1     ; every key is unique according to =
    {1.0 "floatone", 1 "intone", 3/2 "ratio1.5", 1.5M "bigdec1.5",
     1.0M "bigdecone"}
    user=> (dissoc m1 1 3/2)
    {1.0 "floatone", 1.5M "bigdec1.5", 1.0M "bigdecone"}

    ;; compare treats 1.0, 1, 1.0M as equal, so first of those keys
    ;; wins.  Similarly for 1.5M and 3/2.  Note that the last *value*
    ;; for any equal key wins, as you should expect when assoc'ing
    ;; key/vals to a map.
    user=> (def m2 (sorted-map 1.0 "floatone" 1 "intone" 1.0M "bigdecone"
                               1.5M "bigdec1.5" 3/2 "ratio1.5"))
    #'user/m2
    user=> m2
    {1.0 "bigdecone", 1.5M "ratio1.5"}
    user=> (dissoc m2 1 3/2)
    {}       ; removing a key only needs equality according to compare

You may search an unsorted map for any value with no exception.

    user=> (m1 1)
    "intone"
    user=> (m1 "a")
    nil     ; no exception, just nil indicating no such key "a"

Searching sorted maps calls the comparator with the searched-for value
and some of the keys in the map, which throws an exception if the
comparator does.

    user=> (m2 1)
    "bigdecone"
    user=> (m2 "a")   ; this gives exception from compare
    ClassCastException java.lang.Double cannot be cast to java.lang.String  java.lang.String.compareTo (String.java:108)

Sorted maps maintain the key/value pairs in sorted order by key using
a persistent red-black tree data structure.  It takes O(log N) time to
add or remove a key/value pair, but the constant factors involved are
typically larger than for unsorted maps.

See also: sorted-map-by, compare, hash-map, assoc, dissoc, keys, vals,
          subseq, rsubseq
