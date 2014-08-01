;; Require clojure.java.io and call its file function:

user=> (require '(clojure.java io))
user=> (clojure.java.io/file "filename")
#&lt;File filename&gt;