user=> (for [x (range 1 6) 
             :let [y (* x x) 
                   z (* x x x)]] 
         [x y z])

([1 1 1] [2 4 8] [3 9 27] [4 16 64] [5 25 125])
