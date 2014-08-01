;; Because it is expanded at compile time, you can also use special 
;; forms as in full blown macros:

user=> (use 'clojure.template)
user=> (do-template [a b] (def a b) d 1 e 2 f 3)
#'user/f
user=> d
1
user=> e
2
user=> f
3

;; and if you are curious why
user=> (use 'clojure.walk)
user=> (macroexpand-all '(do-template [a b] (def a b) d 1 e 2 f 3))
(do (def d 1) (def e 2) (def f 3))

