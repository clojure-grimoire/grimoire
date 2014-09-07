(defn biginteger
  "Coerce to BigInteger"
  {:tag BigInteger
   :added "1.0"
   :static true}
  [x] (cond
       (instance? BigInteger x) x
       (instance? clojure.lang.BigInt x) (.toBigInteger ^clojure.lang.BigInt x)
       (decimal? x) (.toBigInteger ^BigDecimal x)
       (ratio? x) (.bigIntegerValue ^clojure.lang.Ratio x)
       (number? x) (BigInteger/valueOf (long x))
       :else (BigInteger. x)))