(defn prewalk-demo
  "Demonstrates the behavior of prewalk by printing each form as it is
  walked.  Returns form."
  {:added "1.1"}
  [form]
  (prewalk (fn [x] (print "Walked: ") (prn x) x) form))