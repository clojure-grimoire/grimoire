user=> (find-var 'map)
IllegalArgumentException Symbol must be namespace-qualified  clojure.lang.Var.find (Var.java:150)
user=> (find-var 'clojure.core/map)
#'clojure.core/map
user=> (find-var 'clojure.core/qwerty)
nil
