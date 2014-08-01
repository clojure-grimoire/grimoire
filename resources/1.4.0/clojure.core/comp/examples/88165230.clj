user=> (map
         (comp - (partial + 3) (partial * 2))
         [1 2 3 4])
; returns
(-5 -7 -9 -11)