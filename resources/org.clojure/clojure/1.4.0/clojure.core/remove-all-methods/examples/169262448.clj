;; example showing use of multi-methods before and after remove-all-methods
;; after removing all the methods, both circle and line tos functions throw
;; exceptions

user=> (defmulti tos :Ob)
nil
user=> (defn line [p1 p2] {:Ob :line :p1 p1 :p2 p2})
#'user/line
user=> (defn circle [cent rad] {:Ob :circle :cent cent :rad rad})
#'user/circle
user=> (defmethod tos :line [l] (str "Line:" (l :p1) (l :p2)))
#<MultiFn clojure.lang.MultiFn@73aecc3a>
user=> (defmethod tos :circle [c] (str "Circle:" (c :cent) (c :rad)))
#<MultiFn clojure.lang.MultiFn@73aecc3a>
user=> (println (tos (circle [2 3] 3.3)))
Circle:[2 3]3.3
nil
user=> (println (tos (line [1 1][0 0])))
Line:[1 1][0 0]
nil
user=> (remove-all-methods tos)
#<MultiFn clojure.lang.MultiFn@73aecc3a>
user=> (println (tos (circle [2 3] 3.3)))
java.lang.IllegalArgumentException: No method in multimethod 'tos' for dispatch
value: :circle (NO_SOURCE_FILE:0)
user=> (println (tos (line [1 1][0 0])))
java.lang.IllegalArgumentException: No method in multimethod 'tos' for dispatch
value: :line (NO_SOURCE_FILE:0)
user=>