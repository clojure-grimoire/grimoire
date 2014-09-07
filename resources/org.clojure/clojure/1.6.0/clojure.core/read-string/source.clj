(defn read-string
  "Reads one object from the string s.

  Note that read-string can execute code (controlled by *read-eval*),
  and as such should be used only with trusted sources.

  For data structure interop use clojure.edn/read-string"
  {:added "1.0"
   :static true}
  [s] (clojure.lang.RT/readString s))