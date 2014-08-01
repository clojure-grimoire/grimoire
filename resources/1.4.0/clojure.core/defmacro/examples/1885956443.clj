(def dbg 1)

(defmacro chk-flagM
  "Throws an exception if flag does not resolve; else returns flag's value."
  [flag]
  (if (not (resolve flag))
    (throw (Exception. (str 'flag " is not a valid var.")))
    flag))

(defn write-csv-file
  "Writes a csv file using a key and an s-o-s"
  [out-sos out-file]

  (if (>= (chk-flagM dbg) 2)
    (println (first out-sos), "\n", out-file))

  (spit out-file "" :append false)
  (with-open [out-data (io/writer out-file)]
      (csv/write-csv out-data (map #(concat % [""]) out-sos))))

