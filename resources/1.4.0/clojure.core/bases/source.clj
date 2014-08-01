(defn bases
  "Returns the immediate superclass and direct interfaces of c, if any"
  {:added "1.0"
   :static true}
  [^Class c]
  (when c
    (let [i (.getInterfaces c)
          s (.getSuperclass c)]
      (not-empty
       (if s (cons s i) i)))))