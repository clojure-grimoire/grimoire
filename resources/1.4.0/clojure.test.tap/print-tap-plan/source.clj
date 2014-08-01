(defn print-tap-plan
  "Prints a TAP plan line like '1..n'.  n is the number of tests"
  {:added "1.1"}
  [n]
  (println (str "1.." n)))