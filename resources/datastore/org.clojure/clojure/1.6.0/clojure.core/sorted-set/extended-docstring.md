Sorted sets maintain their elements in sorted order, sorted by the
function compare.  Use sorted-set-by to get a different element order.

Sorted sets are in most ways similar to unsorted sets.  Read the docs
for sorted-map to learn how sorted _maps_ differ from unsorted maps.
All of those differences apply equally to how sorted sets differ from
unsorted sets, if you replace 'key/value pairs' with 'elements', and
sorting by keys with sorting by elements.

    user=> (sorted-set 4 2 1)
    #{1 2 4}
    user=> (conj (sorted-set 4 2 1) 3)
    #{1 2 3 4}

    user=> (range 100 0 -5)
    (100 95 90 85 80 75 70 65 60 55 50 45 40 35 30 25 20 15 10 5)
    user=> (def ss (apply sorted-set (range 100 0 -5)))
    #'user/ss
    user=> ss
    #{5 10 15 20 25 30 35 40 45 50 55 60 65 70 75 80 85 90 95 100}
    user=> (first ss)
    5
    user=> (last ss)
    100
    user=> (subseq ss >= 80)
    (80 85 90 95 100)
    user=> (subseq ss > 20 < 60)
    (25 30 35 40 45 50 55)

See also: sorted-set-by, sorted-map, compare, hash-set, conj, disj,
          subseq, rsubseq
