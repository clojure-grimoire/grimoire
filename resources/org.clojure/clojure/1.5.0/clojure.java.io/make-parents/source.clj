(defn make-parents
  "Given the same arg(s) as for file, creates all parent directories of
   the file they represent."
  {:added "1.2"}
  [f & more]
  (when-let [parent (.getParentFile ^File (apply file f more))]
    (.mkdirs parent)))