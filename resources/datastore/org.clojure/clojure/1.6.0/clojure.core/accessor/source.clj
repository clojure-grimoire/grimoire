(defn accessor
  "Returns a fn that, given an instance of a structmap with the basis,
  returns the value at the key.  The key must be in the basis. The
  returned function should be (slightly) more efficient than using
  get, but such use of accessors should be limited to known
  performance-critical areas."
  {:added "1.0"
   :static true}
  [s key]
    (. clojure.lang.PersistentStructMap (getAccessor s key)))