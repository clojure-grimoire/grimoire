  (def ^:dynamic *clojure-version*
    (if (.contains version-string "SNAPSHOT")
      (clojure.lang.RT/assoc clojure-version :interim true)
      clojure-version))