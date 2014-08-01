;; Splits a string on space character and joins 
;; the resulting collection with a line feed character

(use '[clojure.string :only (join split)])

user=> (println
         (join "\n"
           (split "The Quick Brown Fox" #"\s")))
The
Quick
Brown
Fox
nil