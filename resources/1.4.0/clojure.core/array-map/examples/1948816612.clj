user=> (keys (assoc (array-map :foo 10 :bar 20) :baz 30))
(:baz :foo :bar)
; baz is first; :foo and :bar follow the order given to array-map