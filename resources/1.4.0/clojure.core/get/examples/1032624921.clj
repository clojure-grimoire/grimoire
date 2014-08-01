;; 'get' is not the only option
user=> (def my-map {:a 1 :b 2 :c 3})

;; maps act like functions
user=> (my-map :a)
1

;; even keys act like functions
user=> (:b my-map)
2