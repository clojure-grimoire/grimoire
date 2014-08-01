(defn do-report
  "Add file and line information to a test result and call report.
   If you are writing a custom assert-expr method, call this function
   to pass test results to report."
  {:added "1.2"}
  [m]
  (report
   (case
    (:type m)
    :fail (merge (file-and-line (new java.lang.Throwable) 1) m)
    :error (merge (file-and-line (:actual m) 0) m) 
    m)))