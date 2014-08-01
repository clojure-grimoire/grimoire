user=> (map #(vector (first %) (* 2 (second %)))
            {:a 1 :b 2 :c 3})
([:a 2] [:b 4] [:c 6])

user=> (into {} *1)
{:a 2, :b 4, :c 6}
