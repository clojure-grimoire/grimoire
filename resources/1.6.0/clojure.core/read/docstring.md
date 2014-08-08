  Reads the next object from stream, which must be an instance of
  java.io.PushbackReader or some derivee.  stream defaults to the
  current value of *in*.

  Note that read can execute code (controlled by *read-eval*),
  and as such should be used only with trusted sources.

  For data structure interop use clojure.edn/read