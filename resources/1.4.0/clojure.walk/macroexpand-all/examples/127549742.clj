;; differences between macroexpand-1, macroexpand and macroexpand-all 

(require '[clojure.pprint :as pp]
         '[clojure.walk :as walk])

(alter-var-root #'pp/*print-suppress-namespaces* (constantly true))


(defmacro plus [n1 n2]
  `(+ ~n1 ~n2))

(pp/pprint (macroexpand-1 '(plus 3 4)))   ;=> (+ 3 4)
(pp/pprint (macroexpand   '(plus 3 4)))   ;=> (+ 3 4)


(defmacro pl [p1 p2]
  `(plus ~p1 ~p2))

(pp/pprint (macroexpand-1 '(pl 3 4)))     ;=> (plus 3 4)
(pp/pprint (macroexpand   '(pl 3 4)))     ;=> (+ 3 4)


(defmacro minus [m1 m2]
  `(- ~m1 ~m2))

(defmacro calc [c1 c2]
  `(pl ~c1 (minus ~c1 ~c2)))

(pp/pprint (macroexpand-1 '(calc 20 30)))
;=> (pl 20 (minus 20 30))

(pp/pprint (macroexpand   '(calc 20 30)))
;=> (+ 20 (minus 20 30))

(pp/pprint (walk/macroexpand-all '(calc 20 30)))
;=> (+ 20 (- 20 30))