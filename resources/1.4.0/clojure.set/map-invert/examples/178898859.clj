;; Despite being in clojure.set, this has nothing to do with sets. 

user=> (map-invert {:a 1, :b 2})
{2 :b, 1 :a}

;; If there are duplicate keys, one is chosen:

user=> (map-invert {:a 1, :b 1})
{1 :b}

;; I suspect it'd be unwise to depend on which key survives the clash.