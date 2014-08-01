(defn struct
  "Returns a new structmap instance with the keys of the
  structure-basis. vals must be supplied for basis keys in order -
  where values are not supplied they will default to nil."
  {:added "1.0"
   :static true}
  [s & vals]
    (. clojure.lang.PersistentStructMap (construct s vals)))