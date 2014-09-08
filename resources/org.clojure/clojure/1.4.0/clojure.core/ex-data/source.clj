(defn ex-data
  "Alpha - subject to change.
   Returns exception data (a map) if ex is an ExceptionInfo.
   Otherwise returns nil."
  {:added "1.4"}
  [ex]
  (when (instance? ExceptionInfo ex)
    (.getData ^ExceptionInfo ex)))