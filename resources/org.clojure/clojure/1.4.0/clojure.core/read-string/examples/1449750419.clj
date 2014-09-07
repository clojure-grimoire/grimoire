user=> (read-string "; foo\n5")
5

user=> (read-string "#^String x")
x

user=> (read-string "(1)")
(1)

user=> (read-string "(+ 1 2) (- 3 2)")
(+ 1 2)

user=> (read-string "@a")
(clojure.core/deref a)

user=> (read-string "(+ 1 2))))))")
(+ 1 2)

user=> (read-string "::whatever-namespace-you-are-in")
:user/whatever-namespace-you-are-in