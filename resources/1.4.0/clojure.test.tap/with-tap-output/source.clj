(defmacro with-tap-output
  "Execute body with modified test reporting functions that produce
  TAP output"
  {:added "1.1"}
  [& body]
  `(binding [t/report tap-report]
     ~@body))