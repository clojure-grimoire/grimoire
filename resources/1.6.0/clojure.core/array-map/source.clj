(defn array-map
  "Constructs an array-map. If any keys are equal, they are handled as
  if by repeated uses of assoc."
  {:added "1.0"
   :static true}
  ([] (. clojure.lang.PersistentArrayMap EMPTY))
  ([& keyvals]
     (clojure.lang.PersistentArrayMap/createAsIfByAssoc (to-array keyvals))))