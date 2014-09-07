  Defaults to true (or value specified by system property, see below)
  ***This setting implies that the full power of the reader is in play,
  including syntax that can cause code to execute. It should never be
  used with untrusted sources. See also: clojure.edn/read.***

  When set to logical false in the thread-local binding,
  the eval reader (#=) and record/type literal syntax are disabled in read/load.
  Example (will fail): (binding [*read-eval* false] (read-string "#=(* 2 21)"))

  The default binding can be controlled by the system property
  'clojure.read.eval' System properties can be set on the command line
  like this:

  java -Dclojure.read.eval=false ...

  The system property can also be set to 'unknown' via
  -Dclojure.read.eval=unknown, in which case the default binding
  is :unknown and all reads will fail in contexts where *read-eval*
  has not been explicitly bound to either true or false. This setting
  can be a useful diagnostic tool to ensure that all of your reads
  occur in considered contexts. You can also accomplish this in a
  particular scope by binding *read-eval* to :unknown
  