user=> (def c 5)
user=> (->> c (+ 3) (/ 2) (- 1))                          
3/4

;; and if you are curious why
user=> (use 'clojure.walk)
user=> (macroexpand-all '(->> c (+ 3) (/ 2) (- 1)))
(- 1 (/ 2 (+ 3 c)))

