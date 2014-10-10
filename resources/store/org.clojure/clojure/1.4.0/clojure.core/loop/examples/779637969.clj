(loop [x 10]
  (when (> x 1)
    (println x)
    (recur (- x 2))))