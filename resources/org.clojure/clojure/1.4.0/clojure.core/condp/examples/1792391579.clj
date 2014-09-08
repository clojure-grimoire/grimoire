;; Taken from the excellent clojure tutorial:
;; http://java.ociweb.com/mark/clojure/article.html

user=> (print "Enter a number: ")
user=> (flush) ; stays in a buffer otherwise
user=> (let [reader (java.io.BufferedReader. *in*) ; stdin
             line (.readLine reader)
             value (try
                     (Integer/parseInt line)
                     (catch NumberFormatException e line))] ;use string val if not int
         (println
           (condp = value
             1 "one"
             2 "two"
             3 "three"
             (str "unexpected value, \"" value \")))
         (println
           (condp instance? value
             Number (* value 2)
             String (* (count value) 2))))
