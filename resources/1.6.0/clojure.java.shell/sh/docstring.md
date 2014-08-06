  Passes the given strings to Runtime.exec() to launch a sub-process.

  Options are

  :in      may be given followed by any legal input source for
           clojure.java.io/copy, e.g. InputStream, Reader, File, byte[],
           or String, to be fed to the sub-process's stdin.
  :in-enc  option may be given followed by a String, used as a character
           encoding name (for example "UTF-8" or "ISO-8859-1") to
           convert the input string specified by the :in option to the
           sub-process's stdin.  Defaults to UTF-8.
           If the :in option provides a byte array, then the bytes are passed
           unencoded, and this option is ignored.
  :out-enc option may be given followed by :bytes or a String. If a
           String is given, it will be used as a character encoding
           name (for example "UTF-8" or "ISO-8859-1") to convert
           the sub-process's stdout to a String which is returned.
           If :bytes is given, the sub-process's stdout will be stored
           in a byte array and returned.  Defaults to UTF-8.
  :env     override the process env with a map (or the underlying Java
           String[] if you are a masochist).
  :dir     override the process dir with a String or java.io.File.

  You can bind :env or :dir for multiple operations using with-sh-env
  and with-sh-dir.

  sh returns a map of
    :exit => sub-process's exit code
    :out  => sub-process's stdout (as byte[] or String)
    :err  => sub-process's stderr (String via platform default encoding)