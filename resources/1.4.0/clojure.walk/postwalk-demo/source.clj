(defn postwalk-demo
  "Demonstrates the behavior of postwalk by printing each form as it is
  walked.  Returns form."
  {:added "1.1"}
  [form]
  (postwalk (fn [x] (print "Walked: ") (prn x) x) form))