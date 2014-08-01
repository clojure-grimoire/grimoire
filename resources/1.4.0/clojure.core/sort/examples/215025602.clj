user=> (sort [3 1 2 4])
(1 2 3 4)

user=> (sort > (vals {:foo 5, :bar 2, :baz 10}))
(10 5 2)

;; do not do this, use sort-by instead
user=> (sort #(compare (last %1) (last %2)) {:b 1 :c 3 :a  2})
([:b 1] [:a 2] [:c 3])

;; like this:
user=> (sort-by last {:b 1 :c 3 :a 2})
([:b 1] [:a 2] [:c 3])