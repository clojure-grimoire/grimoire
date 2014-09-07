;; WARNING: You SHOULD NOT use clojure.core/read or
;; clojure.core/read-string to read data from untrusted sources.  They
;; were designed only for reading Clojure code and data from trusted
;; sources (e.g. files that you know you wrote yourself, and no one
;; else has permission to modify them).

;; Instead, either:

;; (1) Use another data serialization format such as JSON, XML,
;; etc. and a library for reading them that you trust not to have
;; vulnerabilities, or

;; (2) if you want a serialization format that can be read safely and
;; looks like Clojure data structures, use edn
;; (https://github.com/edn-format/edn).  For Clojure 1.3 and later,
;; the tools.reader contrib library provides an edn reader
;; (http://github.com/clojure/tools.reader).  There is also
;; clojure.edn/read and clojure.edn/read-string provided in Clojure
;; 1.5.

;; You definitely should not use clojure.core/read or read-string if
;; *read-eval* has its default value of true, because an attacker
;; could cause your application to execute arbitrary code while it is
;; reading.  Example:

user=> (read-string "#=(clojure.java.shell/sh \"echo\" \"hi\")")
{:exit 0, :out "hi\n", :err ""}

;; It is straightforward to modify the example above into more
;; destructive ones that remove all of your files, copy them to
;; someone else's computer over the Internet, install Trojans, etc.

;; Even if you do bind *read-eval* to false first, like so:

(defn read-string-unsafely [s]
  (binding [*read-eval* false]
    (read-string s)))

;; you may hope you are safe reading untrusted data that way, but in
;; Clojure 1.4 and earlier, an attacker can send data that causes your
;; system to execute arbitrary Java constructors.  Most of these are
;; benign, but it only takes one to ruin your application's day.
;; Examples that should scare you:

;; This causes a socket to be opened, as long as the JVM sandboxing
;; allows it.
(read-string-unsafely "#java.net.Socket[\"www.google.com\" 80]")

;; This causes precious-file.txt to be created if it doesn't exist, or
;; if it does exist, its contents will be erased (given appropriate
;; JVM sandboxing permissions, and underlying OS file permissions).
(read-string-unsafely "#java.io.FileWriter[\"precious-file.txt\"]")

;; The particular issue of executing arbitrary Java constructors used
;; in the examples above no longer works in Clojure 1.5 when
;; *read-eval* is false.  Even so, you SHOULD NEVER USE
;; clojure.core/read or clojure.core/read-string for reading untrusted
;; data.  Use an edn reader or a different data serialization format.

;; Why should I do this, you may ask, if Clojure 1.5 closes the Java
;; constructor hole?  Because clojure.core/read and read-string are
;; designed to be able to do dangerous things, and they are not
;; documented nor promised to be safe from unwanted side effects.  If
;; you use them for reading untrusted data, and a dangerous side
;; effect is found in the future, you will be told that you are using
;; the wrong tool for the job.  clojure.edn/read and read-string, and
;; the tools.reader.edn library, are documented to be safe from
;; unwanted side effects, and if any bug is found in this area it
;; should get quick attention and corrected.

;; If you understand all of the above, and want to use read or
;; read-string to read data from a _trusted_ source, continue on
;; below.

;; read wants its reader arg (or *in*) to be a java.io.PushbackReader.
;; with-open closes r after the with-open body is done.  *read-eval*
;; specifies whether to allow #=() forms when reading, and evaluate
;; them as a side effect while reading.

(defn read-from-file-with-trusted-contents [filename]
  (with-open [r (java.io.PushbackReader.
                 (clojure.java.io/reader filename))]
    (binding [*read-eval* false]
      (read r))))

user=> (spit "testfile.txt" "{:a 1 :b 2 :c 3}")
nil
user=> (read-from-file-with-trusted-contents "testfile.txt")
{:a 1, :b 2, :c 3}
