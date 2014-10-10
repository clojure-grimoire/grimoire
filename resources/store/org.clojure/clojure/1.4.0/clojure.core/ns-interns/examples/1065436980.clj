user=> (take 2 (ns-interns `clojure.core))
([sorted-map #'clojure.core/sorted-map] [read-line #'clojure.core/read-line])

user=> (take 5 (sort (keys (ns-interns `clojure.java.io))))
(Coercions IOFactory append? as-file as-relative-path)

user=> (count (ns-interns `clojure.core)) ; only 621 functions to learn :-)
621
user=>