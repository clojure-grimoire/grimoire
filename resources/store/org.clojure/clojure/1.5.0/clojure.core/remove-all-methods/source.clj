(defn remove-all-methods
  "Removes all of the methods of multimethod."
  {:added "1.2"
   :static true} 
 [^clojure.lang.MultiFn multifn]
 (.reset multifn))