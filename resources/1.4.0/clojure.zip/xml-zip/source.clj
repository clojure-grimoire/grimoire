(defn xml-zip
  "Returns a zipper for xml elements (as from xml/parse),
  given a root element"
  {:added "1.0"}
  [root]
    (zipper (complement string?) 
            (comp seq :content)
            (fn [node children]
              (assoc node :content (and children (apply vector children))))
            root))