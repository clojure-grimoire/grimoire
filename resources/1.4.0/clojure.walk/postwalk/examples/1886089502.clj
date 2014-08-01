(use 'clojure.walk)
(let [counter (atom -1)]
   (postwalk (fn [x]
                [(swap! counter inc) x])
              {:a 1 :b 2}))

=> [6 {2 [[0 :a] [1 1]], 5 [[3 :b] [4 2]]}] 