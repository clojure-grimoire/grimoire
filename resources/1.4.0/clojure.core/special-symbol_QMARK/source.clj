(defn special-symbol?
  "Returns true if s names a special form"
  {:added "1.0"
   :static true}
  [s]
    (contains? (. clojure.lang.Compiler specials) s))