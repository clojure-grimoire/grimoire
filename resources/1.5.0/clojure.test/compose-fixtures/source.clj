(defn compose-fixtures
  "Composes two fixture functions, creating a new fixture function
  that combines their behavior."
  {:added "1.1"}
  [f1 f2]
  (fn [g] (f1 (fn [] (f2 g)))))