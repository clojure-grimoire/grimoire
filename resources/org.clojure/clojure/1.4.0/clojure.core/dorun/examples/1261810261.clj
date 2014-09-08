user=> (let [x (atom 0)]
         (dorun (take 10 (repeatedly #(swap! x inc))))
         @x)
10