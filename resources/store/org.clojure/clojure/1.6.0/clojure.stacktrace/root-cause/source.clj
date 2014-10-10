(defn root-cause
  "Returns the last 'cause' Throwable in a chain of Throwables."
  {:added "1.1"}
  [tr]
  (if-let [cause (.getCause tr)]
    (recur cause)
    tr))