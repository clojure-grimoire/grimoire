;; As of Clojure 1.3, vars need to be explicitly marked as ^:dynamic in order for
;; them to be dynamically rebindable:

user=> (def ^:dynamic x 1)
user=> (def ^:dynamic y 1)
user=> (+ x y)
2

;; Within the scope of the binding, x = 2 and y = 3

user=> (binding [x 2 y 3]
         (+ x y))
5

;; But once you leave the binding's scope, x and y maintain their original
;; bindings:

user=> (+ x y)
2