(defn mapcat
  "Returns the result of applying concat to the result of applying map
  to f and colls.  Thus function f should return a collection."
  {:added "1.0"
   :static true}
  [f & colls]
    (apply concat (apply map f colls)))