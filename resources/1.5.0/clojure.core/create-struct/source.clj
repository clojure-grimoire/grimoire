(defn create-struct
  "Returns a structure basis object."
  {:added "1.0"
   :static true}
  [& keys]
    (. clojure.lang.PersistentStructMap (createSlotMap keys)))