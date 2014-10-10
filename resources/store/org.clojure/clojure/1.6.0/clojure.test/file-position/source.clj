(defn file-position
  "Returns a vector [filename line-number] for the nth call up the
  stack.

  Deprecated in 1.2: The information needed for test reporting is
  now on :file and :line keys in the result map."
  {:added "1.1"
   :deprecated "1.2"}
  [n]
  (let [^StackTraceElement s (nth (.getStackTrace (new java.lang.Throwable)) n)]
    [(.getFileName s) (.getLineNumber s)]))