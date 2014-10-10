;looping is recursive in Clojure, the loop construct is a hack so that something like tail-recursive-optimization works in clojure.
user=> (defn my-re-seq [re string]
         "Something like re-seq"
         (let [matcher (re-matcher re string)]

           (loop [match (re-find matcher) ;loop starts with 2 set arguments
                  result []]
             (if-not match
               result
               (recur (re-find matcher)    ;loop with 2 new arguments
                      (conj result match))))))

#'user/my-re-seq

user=> (my-re-seq #"\d" "0123456789")
["0" "1" "2" "3" "4" "5" "6" "7" "8" "9"]

