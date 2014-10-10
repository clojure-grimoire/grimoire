(defn ratio?
  "Returns true if n is a Ratio"
  {:added "1.0"
   :static true}
  [n] (instance? clojure.lang.Ratio n))