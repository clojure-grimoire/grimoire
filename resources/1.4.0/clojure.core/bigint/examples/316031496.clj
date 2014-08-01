user> (= (bigint 42) (clojure.lang.BigInt/fromBigInteger (BigInteger. "42"))
true
user> (= 42N (bigint 42))
true
user> (= 42 (bigint 42))
true
user> (= 42 (clojure.lang.BigInt/fromBigInteger (BigInteger. "42"))
true
