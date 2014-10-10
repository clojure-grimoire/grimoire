(defn append!
  ".adds x to acc and returns acc"
  {:added "1.5"}
  [^java.util.Collection acc x]
  (doto acc (.add x)))