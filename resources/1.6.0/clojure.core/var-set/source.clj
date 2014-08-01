(defn var-set
  "Sets the value in the var object to val. The var must be
 thread-locally bound."
  {:added "1.0"
   :static true}
  [^clojure.lang.Var x val] (. x (set val)))