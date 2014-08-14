;; An alternate to the example in the documentation is 

user=> (are [result arg-map] (= result (+ (:x arg-map) (:y arg-map)))
             5      {:x 2 :y 3},
             10     {:x 6 :y 4})


