(defn inspect
  "creates a graphical (Swing) inspector on the supplied object"
  {:added "1.0"}
  [x]
  (doto (JFrame. "Clojure Inspector")
    (.add
      (doto (JPanel. (BorderLayout.))
        (.add (doto (JToolBar.)
                (.add (JButton. "Back"))
                (.addSeparator)
                (.add (JButton. "List"))
                (.add (JButton. "Table"))
                (.add (JButton. "Bean"))
                (.add (JButton. "Line"))
                (.add (JButton. "Bar"))
                (.addSeparator)
                (.add (JButton. "Prev"))
                (.add (JButton. "Next")))
              BorderLayout/NORTH)
        (.add
          (JScrollPane. 
            (doto (JTable. (list-model (list-provider x)))
              (.setAutoResizeMode JTable/AUTO_RESIZE_LAST_COLUMN)))
          BorderLayout/CENTER)))
    (.setSize 400 400)
    (.setVisible true)))