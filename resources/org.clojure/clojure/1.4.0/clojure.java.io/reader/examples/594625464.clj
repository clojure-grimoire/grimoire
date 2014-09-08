(with-open [rdr (clojure.java.io/reader "/tmp/foo.txt")]
    (reduce conj [] (line-seq rdr)))