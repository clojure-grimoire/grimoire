;; define a multi-method, then demonstrate that you may use 
;; get-method in the same way you can call the method directly

user=> (defmulti tos :Ob)
#'user/tos
user=> (defn line [p1 p2] {:Ob :line :p1 p1 :p2 p2})
#'user/line
user=> (defn circle [cent rad] {:Ob :circle :cent cent :rad rad})
#'user/circle
user=> (defmethod tos :line [l] (str "Line:" (l :p1) (l :p2)))
#<MultiFn clojure.lang.MultiFn@a0b1cd0>
user=> (defmethod tos :circle [c] (str "Circle:" (c :cent) (c :rad)))
#<MultiFn clojure.lang.MultiFn@a0b1cd0>
user=> (println (tos (circle [2 3] 3.3)))
Circle:[2 3]3.3
nil
user=> (println (tos (line [1 1][0 0])))
Line:[1 1][0 0]
nil
user=> (println ((get-method tos :line) (line [1 2][3 4]) ))
Line:[1 2][3 4]
nil
user=>