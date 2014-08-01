;; sort list of maps by multiple values
user => (sort-by (juxt :a :b) [{:a 1 :b 3} {:a 1 :b 2} {:a 2 :b 1}]
[{:a 1 :b 2} {:a 1 :b 3} {:a 2 :b 1}]