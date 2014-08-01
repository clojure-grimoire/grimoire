(defn class?
  "Returns true if x is an instance of Class"
  {:added "1.0"
   :static true}
  [x] (instance? Class x))