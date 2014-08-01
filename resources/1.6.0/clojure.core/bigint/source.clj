(defn bigint
  "Coerce to BigInt"
  {:tag clojure.lang.BigInt
   :static true
   :added "1.3"}
  [x] (cond
       (instance? clojure.lang.BigInt x) x
       (instance? BigInteger x) (clojure.lang.BigInt/fromBigInteger x)
       (decimal? x) (bigint (.toBigInteger ^BigDecimal x))
       (float? x)  (bigint (. BigDecimal valueOf (double x)))
       (ratio? x) (bigint (.bigIntegerValue ^clojure.lang.Ratio x))
       (number? x) (clojure.lang.BigInt/valueOf (long x))
       :else (bigint (BigInteger. x))))