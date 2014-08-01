;; contrast to example code for sequential?
;;
user> (coll? '(1 2 3))
true
user> (coll? [1 2 3])
true
user> (coll? (range 1 5))
true
user> (coll? 1)
false
user> (coll? {:a 2 :b 1})   
true
user> (coll? {:a 2 :b 1})  ; in contrast to sequential?, coll? returns true for a hash map
true
user> (sequential? {:a 2 :b 1})
false