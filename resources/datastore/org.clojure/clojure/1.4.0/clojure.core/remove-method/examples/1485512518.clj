;; long example showing setting up a multi-method, then removing one of the 
;; methods, showing the multi-method has been removed

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
user=> (def cc (circle [2 3] 3.3))
#'user/cc
user=> (def ll (line [1 1][0 0]))
#'user/ll
user=> (tos cc)
"Circle:[2 3]3.3"
user=> (tos ll)
"Line:[1 1][0 0]"
user=> (remove-method tos :line)
#<MultiFn clojure.lang.MultiFn@73aecc3a>
user=> (tos ll)
java.lang.IllegalArgumentException: No method in multimethod 'tos' for dispatch
value: :line (NO_SOURCE_FILE:0)
user=>
