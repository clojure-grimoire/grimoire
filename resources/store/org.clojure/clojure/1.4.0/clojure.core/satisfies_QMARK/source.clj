(defn satisfies? 
  "Returns true if x satisfies the protocol"
  {:added "1.2"}
  [protocol x]
  (boolean (find-protocol-impl protocol x)))