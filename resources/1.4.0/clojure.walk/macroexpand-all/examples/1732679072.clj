user=> (use 'clojure.walk)
user=> (macroexpand-all '(-> c (+ 3) (* 2)))
(* (+ c 3) 2)