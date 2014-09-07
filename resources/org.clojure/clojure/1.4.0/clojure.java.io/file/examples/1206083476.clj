; Use clojure.java.io to read in resources from the classpath

(ns rescue.core
  (:require [clojure.java.io :as io] ))

; Populate the file on the command line:  
;   echo "Hello Resources!" > resources/hello.txt
(def data-file (io/file
                 (io/resource 
                   "hello.txt" )))
(defn -main []
  (println (slurp data-file)) )
; When do "lein run"
; => Hello Resources!