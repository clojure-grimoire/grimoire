(defn read-string
  "Reads one object from the string s. Returns nil when s is nil or empty.

  Reads data in the edn format (subset of Clojure data):
  http://edn-format.org

  opts is a map as per clojure.edn/read"
  {:added "1.5"}
  ([s] (read-string {:eof nil} s))
  ([opts s] (when s (clojure.lang.EdnReader/readString s opts))))