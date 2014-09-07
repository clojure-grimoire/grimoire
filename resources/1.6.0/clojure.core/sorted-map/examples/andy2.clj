;; Both unsorted and sorted maps follow the rule of 'first equal key to
;; be added wins'.  The difference is in what keys they consider to be
;; equal: unsorted uses =, sorted uses compare or a custom comparator.

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
