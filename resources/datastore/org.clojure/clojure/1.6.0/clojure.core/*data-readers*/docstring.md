  Map from reader tag symbols to data reader Vars.

  When Clojure starts, it searches for files named 'data_readers.clj'
  at the root of the classpath. Each such file must contain a literal
  map of symbols, like this:

      {foo/bar my.project.foo/bar
       foo/baz my.project/baz}

  The first symbol in each pair is a tag that will be recognized by
  the Clojure reader. The second symbol in the pair is the
  fully-qualified name of a Var which will be invoked by the reader to
  parse the form following the tag. For example, given the
  data_readers.clj file above, the Clojure reader would parse this
  form:

      #foo/bar [1 2 3]

  by invoking the Var #'my.project.foo/bar on the vector [1 2 3]. The
  data reader function is invoked on the form AFTER it has been read
  as a normal Clojure data structure by the reader.

  Reader tags without namespace qualifiers are reserved for
  Clojure. Default reader tags are defined in
  clojure.core/default-data-readers but may be overridden in
  data_readers.clj or by rebinding this Var.