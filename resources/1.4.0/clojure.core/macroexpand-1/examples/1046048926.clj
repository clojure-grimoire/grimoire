user=> (macroexpand-1 '(-> c (+ 3) (* 2)))
(clojure.core/-> (clojure.core/-> c (+ 3)) (* 2))