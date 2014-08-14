(defn write-object
  "Serializes an object to disk so it can be opened again later.
   Careful: It will overwrite an existing file at file-path."
  [obj file-path]
    (with-open [wr (writer file-path)]
      (.write wr (pr-str obj)))))