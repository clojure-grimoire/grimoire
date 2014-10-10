(defn split
  "Splits string on a regular expression.  Optional argument limit is
  the maximum number of splits. Not lazy. Returns vector of the splits."
  {:added "1.2"}
  ([^CharSequence s ^Pattern re]
     (LazilyPersistentVector/createOwning (.split re s)))
  ([ ^CharSequence s ^Pattern re limit]
     (LazilyPersistentVector/createOwning (.split re s limit))))