(defn dec
  "Returns a number one less than num. Does not auto-promote
  longs, will throw on overflow. See also: dec'"
  {:inline (fn [x] `(. clojure.lang.Numbers (~(if *unchecked-math* 'unchecked_dec 'dec) ~x)))
   :added "1.2"}
  [x] (. clojure.lang.Numbers (dec x)))