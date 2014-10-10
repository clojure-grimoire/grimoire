(defn delete-file
  "Delete file f. Raise an exception if it fails unless silently is true."
  {:added "1.2"}
  [f & [silently]]
  (or (.delete (file f))
      silently
      (throw (java.io.IOException. (str "Couldn't delete " f)))))