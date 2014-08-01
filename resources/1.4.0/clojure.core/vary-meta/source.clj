(defn vary-meta
 "Returns an object of the same type and value as obj, with
  (apply f (meta obj) args) as its metadata."
 {:added "1.0"
   :static true}
 [obj f & args]
  (with-meta obj (apply f (meta obj) args)))