(defn name
  "Returns the name String of a string, symbol or keyword."
  {:tag String
   :added "1.0"
   :static true}
  [x]
  (if (string? x) x (. ^clojure.lang.Named x (getName))))