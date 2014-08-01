(defn ^:static chunk-append [^clojure.lang.ChunkBuffer b x]
  (.add b x))