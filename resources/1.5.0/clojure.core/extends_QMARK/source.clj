(defn extends? 
  "Returns true if atype extends protocol"
  {:added "1.2"}
  [protocol atype]
  (boolean (or (implements? protocol atype) 
               (get (:impls protocol) atype))))