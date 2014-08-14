(defn ^URL resource
  "Returns the URL for a named resource. Use the context class loader
   if no loader is specified."
  {:added "1.2"}
  ([n] (resource n (.getContextClassLoader (Thread/currentThread))))
  ([n ^ClassLoader loader] (.getResource loader n)))