(defn integer?
  "Returns true if n is an integer"
  {:added "1.0"
   :static true}
  [n]
  (or (instance? Integer n)
      (instance? Long n)
      (instance? clojure.lang.BigInt n)
      (instance? BigInteger n)
      (instance? Short n)
      (instance? Byte n)))