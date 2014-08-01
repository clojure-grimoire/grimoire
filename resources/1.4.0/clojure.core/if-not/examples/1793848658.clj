user=> (defn has-neg [coll] 
  (if-not (empty? coll)   ;;  = (if (not (empty? coll)) ...
    (or (neg? (first coll)) (recur (rest coll)))))
#'user/has-neg

user=> (has-neg [])
nil 

user=> (has-neg [1 2 -3 4])
true