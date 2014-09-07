;; create a file using spit, then show its contents using slurp
;; delete it and verify that it has been deleted by trying to print its
;; contents again

user=> (require '[clojure.java.io :as io])
nil
user=> (spit "stuff.txt" "blurp")
nil
user=> (println (slurp "stuff.txt"))
blurp
nil
user=> (io/delete-file "stuff.txt")
true
user=> (println (slurp "stuff.txt"))
java.io.FileNotFoundException: stuff.txt (The system cannot find the file specif
ied) (NO_SOURCE_FILE:0)
user=>