(defn xml-seq
  "A tree seq on the xml elements as per xml/parse"
  {:added "1.0"
   :static true}
  [root]
    (tree-seq
     (complement string?)
     (comp seq :content)
     root))