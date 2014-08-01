user=> (use '[clojure.java.shell :only [sh]])
nil

;; note that the options, like :in, have to go at the end of arglist
;; advantage of piping-in thru stdin is less need for quoting/escaping
user=> (println (:out (sh "cat" "-" :in "Printing input from stdin with funny chars like ' \" $@ & ")))
Printing input from stdin with funny chars like ' " $@ & 
nil