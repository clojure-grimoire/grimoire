user=> (macroexpand '(-> c (+ 3) (* 2)))    
(* (clojure.core/-> c (+ 3)) 2)
