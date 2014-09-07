(defmacro with-junit-output
  "Execute body with modified test-is reporting functions that write
  JUnit-compatible XML output."
  {:added "1.1"}
  [& body]
  `(binding [t/report junit-report
             *var-context* (list)
             *depth* 1]
     (t/with-test-out
       (println "<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
       (println "<testsuites>"))
     (let [result# ~@body]
       (t/with-test-out (println "</testsuites>"))
       result#)))