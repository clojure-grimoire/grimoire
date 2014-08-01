;; print-dup can be used for basic serialization
;; the following methods write/read clojure forms to/from a file

(defn to-file
  "Save a clojure form to a file"
  [#^java.io.File file form]
  (with-open [w (java.io.FileWriter. file)]
    (print-dup form w)))
 
(defn from-file
  "Load a clojure form from file."
  [#^java.io.File file]
  (with-open [r (java.io.PushbackReader. (java.io.FileReader. file))]
     (read r)))