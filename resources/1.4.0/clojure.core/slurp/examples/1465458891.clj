;; On Linux, some JVMs have a bug where they cannot read a file in the /proc
;; filesystem as a buffered stream or reader.  A workaround to this JVM issue
;; is to open such a file as unbuffered:
(slurp (java.io.FileReader. "/proc/cpuinfo"))