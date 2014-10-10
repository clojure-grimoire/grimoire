;; `contains?` is straightforward for maps:
user=> (contains? {:a 1} :a)
true
user=> (contains? {:a nil} :a)
true
user=> (contains? {:a 1} :b)
false


;; It's likely to surprise you for other sequences because it's 
;; about *indices*, not *contents*:

user=> (contains? [:a :b :c] :b)
false

user=> (contains? [:a :b :c] 2)
true

user=> (contains? "f" 0)
true

user=> (contains? "f" 1)
false


;; It can be applied to non-sequences:

user=> (contains? 5 3)
false


;; Although lists are sequences, `contains?` seems to always return
;; `false` for them. (Clojure 1.1)
;;
;; Note, this no longer works in Clojure 1.5, throwing an 
;; IllegalArgumentException.

user=> (contains? '(1 2 3) 1) => false

