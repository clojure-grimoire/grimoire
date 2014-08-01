;; If you save this program as showargs.clj on a Unix-like system, then the
;; following command will produce the output shown.

;; % java -classpath clojure-1.2.0.jar clojure.main showargs.clj arg1 2 "whitespace in most command shells if you quote"
;; arg='arg1'
;; arg='2'
;; arg='whitespace in most command shells if you quote'
;; 
;; 
;; Second arg is string 2, not number 2.

(ns com.demo.showargs)

(doseq [arg *command-line-args*]
  (printf "arg='%s'\n" arg))

(if (= "2" (second *command-line-args*))
  (println "\n\nSecond arg is string 2, not number 2."))
