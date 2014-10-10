(defn reduced?
  "Returns true if x is the result of a call to reduced"
  {:inline (fn [x] `(clojure.lang.RT/isReduced ~x ))
   :inline-arities #{1}
   :added "1.5"}
  ([x] (clojure.lang.RT/isReduced x)))