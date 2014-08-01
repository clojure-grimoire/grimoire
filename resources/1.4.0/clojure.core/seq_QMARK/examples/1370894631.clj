;; contrast to example code for sequential?
;;
user> (seq? '(1 2 3))
true
user> (seq? [1 2 3])   ; for sequential?, returns true
false
user> (seq? (range 1 5))
true
user> (seq? 1)
false
user> (seq? {:a 2 :b 1})
false
user> 