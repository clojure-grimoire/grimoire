(defn read-string
  "Reads one object from the string s"
  {:added "1.0"
   :static true}
  [s] (clojure.lang.RT/readString s))