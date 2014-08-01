(defmacro defstruct
  "Same as (def name (create-struct keys...))"
  {:added "1.0"
   :static true}
  [name & keys]
  `(def ~name (create-struct ~@keys)))