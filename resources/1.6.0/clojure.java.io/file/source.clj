(defn ^File file
  "Returns a java.io.File, passing each arg to as-file.  Multiple-arg
   versions treat the first argument as parent and subsequent args as
   children relative to the parent."
  {:added "1.2"}
  ([arg]                      
     (as-file arg))
  ([parent child]             
     (File. ^File (as-file parent) ^String (as-relative-path child)))
  ([parent child & more]
     (reduce file (file parent child) more)))