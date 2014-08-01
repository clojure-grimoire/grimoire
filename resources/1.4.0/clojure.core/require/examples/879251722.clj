;; alias clojure.java.io as io
user=> (require '[clojure.java.io :as io])
nil

user=> (io/file "Filename")
#<File Filename>

;; alias clojure.java.io as io using prefixes
user=> (require '(clojure.java [io :as io2])
nil

user=> (io2/file "Filename")
#<File Filename>