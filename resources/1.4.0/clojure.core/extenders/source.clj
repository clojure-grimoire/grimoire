(defn extenders 
  "Returns a collection of the types explicitly extending protocol"
  {:added "1.2"}
  [protocol]
  (keys (:impls protocol)))