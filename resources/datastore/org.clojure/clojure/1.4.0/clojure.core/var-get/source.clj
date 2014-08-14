(defn var-get
  "Gets the value in the var object"
  {:added "1.0"
   :static true}
  [^clojure.lang.Var x] (. x (get)))