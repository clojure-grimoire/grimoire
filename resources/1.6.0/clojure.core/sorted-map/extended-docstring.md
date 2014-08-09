Sorted maps maintain their keys in sorted order, sorted by the
function compare.  Use sorted-map-by to get a different key order.

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

Sorted maps maintain the key/value pairs in sorted order by key using
a persistent red-black tree data structure.  It takes O(log N) time to
add or remove a key/value pair, but the constant factors involved are
typically larger than for unsorted maps.
