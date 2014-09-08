;; Count lines of a file (loses head):
user=> (with-open [rdr (clojure.java.io/reader "/etc/passwd")]
         (count (line-seq rdr)))

