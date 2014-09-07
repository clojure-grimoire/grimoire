(defn delay?
  "returns true if x is a Delay created with delay"
  {:added "1.0"
   :static true}
  [x] (instance? clojure.lang.Delay x))