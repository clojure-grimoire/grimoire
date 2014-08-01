(defn startparse-sax [s ch]
  (.. SAXParserFactory (newInstance) (newSAXParser) (parse s ch)))