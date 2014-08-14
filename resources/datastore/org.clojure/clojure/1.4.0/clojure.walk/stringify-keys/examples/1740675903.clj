(use 'clojure.walk)

(stringify-keys {:a 1 :b 2})
;=> {"a" 1, "b" 2}