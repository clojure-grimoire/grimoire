user=> (get [1 2 3] 1)
2
user=> (get [1 2 3] 5)
nil
user=> (get {:a 1 :b 2} :b)
2
user=> (get {:a 1 :b 2} :z "missing")
"missing"

