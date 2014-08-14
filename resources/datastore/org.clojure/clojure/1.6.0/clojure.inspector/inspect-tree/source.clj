(defn inspect-tree 
  "creates a graphical (Swing) inspector on the supplied hierarchical data"
  {:added "1.0"}
  [data]
  (doto (JFrame. "Clojure Inspector")
    (.add (JScrollPane. (JTree. (tree-model data))))
    (.setSize 400 600)
    (.setVisible true)))