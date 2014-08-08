  Parses and loads the source s, which can be a File, InputStream or
  String naming a URI. Returns a tree of the xml/element struct-map,
  which has the keys :tag, :attrs, and :content. and accessor fns tag,
  attrs, and content. Other parsers can be supplied by passing
  startparse, a fn taking a source and a ContentHandler and returning
  a parser