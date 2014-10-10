user=> (for [x [0 1 2 3 4 5]
             :let [y (* x 3)]
             :when (even? y)]
         y)
(0 6 12)
