(defn ^:static chunked-seq? [s]
  (instance? clojure.lang.IChunkedSeq s))