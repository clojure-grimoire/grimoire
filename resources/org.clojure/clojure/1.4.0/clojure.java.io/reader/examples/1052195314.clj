user=> (with-open [rdr (clojure.java.io/reader "http://www.google.com")]
         (printf "%s\n" (clojure.string/join "\n" (line-seq rdr))))
