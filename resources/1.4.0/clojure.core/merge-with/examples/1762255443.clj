;; Use union to merge sets of elements
user=> (use 'clojure.set)
user=> (merge-with union
                   {:a #{1 2 3},   :b #{4 5 6}}
                   {:a #{2 3 7 8}, :c #{1 2 3}})

{:c #{1 2 3}, :a #{1 2 3 7 8}, :b #{4 5 6}}