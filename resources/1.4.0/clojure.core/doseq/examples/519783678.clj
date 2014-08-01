; Keywords :let, :when, and :while are supported, the same as "for"
user=> (doseq [x (range 6)
               :when (odd? x)
               :let [y (* x x)] ]
         (println [x y]) )
[1 1]
[3 9]
[5 25]
nil
user=> (doseq [x (range 99)
               :let [y (* x x)] 
               :while (< y 30)
              ]
         (println [x y]) )
[0 0]
[1 1]
[2 4]
[3 9]
[4 16]
[5 25]
nil
user=> 
