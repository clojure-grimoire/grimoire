(defn struct-map
  "Returns a new structmap instance with the keys of the
  structure-basis. keyvals may contain all, some or none of the basis
  keys - where values are not supplied they will default to nil.
  keyvals can also contain keys not in the basis."
  {:added "1.0"
   :static true}
  [s & inits]
    (. clojure.lang.PersistentStructMap (create s inits)))