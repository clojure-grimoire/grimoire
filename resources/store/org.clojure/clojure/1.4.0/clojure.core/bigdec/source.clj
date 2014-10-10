(defn bigdec
  "Coerce to BigDecimal"
  {:tag BigDecimal
   :added "1.0"
   :static true}
  [x] (cond
       (decimal? x) x
       (float? x) (. BigDecimal valueOf (double x))
       (ratio? x) (/ (BigDecimal. (.numerator x)) (.denominator x))
       (instance? BigInteger x) (BigDecimal. ^BigInteger x)
       (number? x) (BigDecimal/valueOf (long x))
       :else (BigDecimal. x)))