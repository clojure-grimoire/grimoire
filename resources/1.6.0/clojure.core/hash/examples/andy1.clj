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
