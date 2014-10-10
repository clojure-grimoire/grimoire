(defn bases
  "Returns the immediate superclass and direct interfaces of c, if any"
  {:added "1.0"
   :static true}
  [^Class c]
  (when c
    (let [i (seq (.getInterfaces c))
          s (.getSuperclass c)]
      (if s (cons s i) i))))