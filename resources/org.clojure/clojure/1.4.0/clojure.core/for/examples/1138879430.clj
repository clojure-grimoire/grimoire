; Demonstrating difference between :when and :while

user=> (time (dorun (for [x (range 1000) y (range 10000) :when (> x y)] [x y])))
"Elapsed time: 2898.908 msecs"
nil

user=> (time (dorun (for [x (range 1000) y (range 10000) :while (> x y)] [x y])))
"Elapsed time: 293.677 msecs"
nil