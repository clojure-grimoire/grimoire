user=> (for [[x y] '([:a 1] [:b 2] [:c 0]) :when (= y 0)] x)
(:c)
