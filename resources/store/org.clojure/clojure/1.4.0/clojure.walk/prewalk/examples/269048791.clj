;; From http://stackoverflow.com/questions/8089074/idiomatically-iterating-over-a-2-or-higher-dimensional-sequence-in-clojure/8091544#8091544

(def matrix [[1 2 3]
             [4 5 6]
             [7 8 9]])
(use 'clojure.walk :only [prewalk])

(prewalk #(if (number? %) (inc %) %) matrix)
=> [[2 3 4] [5 6 7] [8 9 10]]