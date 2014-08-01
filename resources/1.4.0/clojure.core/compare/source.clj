(defn compare
  "Comparator. Returns a negative number, zero, or a positive number
  when x is logically 'less than', 'equal to', or 'greater than'
  y. Same as Java x.compareTo(y) except it also works for nil, and
  compares numbers and collections in a type-independent manner. x
  must implement Comparable"
  {
   :inline (fn [x y] `(. clojure.lang.Util compare ~x ~y))
   :added "1.0"}
  [x y] (. clojure.lang.Util (compare x y)))