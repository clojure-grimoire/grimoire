;; create a clojure file on the fly using spit
;; then load it into the REPL and use its function

user=> (spit "mycode.clj" "(defn doub [x] (* x 2))")
nil
user=> (load-file "mycode.clj")
#'user/doub
user=> (doub 23)
46
user=>