;; Using a reified FileFilter implementation to obtain only directory files
(.listFiles (java.io.File. ".")
  (reify
    java.io.FileFilter
    (accept [this f]
      (.isDirectory f))))
