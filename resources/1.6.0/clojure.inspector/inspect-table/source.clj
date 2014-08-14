(defn inspect-table 
  "creates a graphical (Swing) inspector on the supplied regular
  data, which must be a sequential data structure of data structures
  of equal length"
  {:added "1.0"}
    [data]
  (doto (JFrame. "Clojure Inspector")
    (.add (JScrollPane. (JTable. (old-table-model data))))
    (.setSize 400 600)
    (.setVisible true)))