(defn parse
  "Parses and loads the source s, which can be a File, InputStream or
  String naming a URI. Returns a tree of the xml/element struct-map,
  which has the keys :tag, :attrs, and :content. and accessor fns tag,
  attrs, and content. Other parsers can be supplied by passing
  startparse, a fn taking a source and a ContentHandler and returning
  a parser"
  {:added "1.0"}
  ([s] (parse s startparse-sax))
  ([s startparse]
    (binding [*stack* nil
              *current* (struct element)
              *state* :between
              *sb* nil]
      (startparse s content-handler)
      ((:content *current*) 0))))