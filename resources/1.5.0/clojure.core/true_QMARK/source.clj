(defn true?
  "Returns true if x is the value true, false otherwise."
  {:tag Boolean,
   :added "1.0"
   :static true}
  [x] (clojure.lang.Util/identical x true))