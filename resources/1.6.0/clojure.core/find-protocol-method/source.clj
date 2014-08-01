(defn find-protocol-method [protocol methodk x]
  (get (find-protocol-impl protocol x) methodk))