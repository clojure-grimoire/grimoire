(defn ^String as-relative-path
  "Take an as-file-able thing and return a string if it is
   a relative path, else IllegalArgumentException."
  {:added "1.2"}
  [x]
  (let [^File f (as-file x)]
    (if (.isAbsolute f)
      (throw (IllegalArgumentException. (str f " is not a relative path")))
      (.getPath f))))