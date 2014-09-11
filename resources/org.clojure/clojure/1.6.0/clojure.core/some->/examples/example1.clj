user=> (some-> {:a {:b {:c 42}}} :a :b :c)
;; 42

user=> (some-> {:a {:b {:c 42}}} :a :d .blowup)
;; nil

;; ported from a draft from Rich Hickey (https://gist.github.com/richhickey/3885504)
