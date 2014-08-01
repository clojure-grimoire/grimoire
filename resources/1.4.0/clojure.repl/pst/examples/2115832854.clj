user=> (pst)
nil

user=> (/ 1 0)
ArithmeticException Divide by zero  clojure.lang.Numbers.divide (Numbers.java:156)

user=> (pst)
ArithmeticException Divide by zero
	clojure.lang.Numbers.divide (Numbers.java:156)
	clojure.lang.Numbers.divide (Numbers.java:3691)
	user/eval13 (NO_SOURCE_FILE:7)
	clojure.lang.Compiler.eval (Compiler.java:6619)
	clojure.lang.Compiler.eval (Compiler.java:6582)
	clojure.core/eval (core.clj:2852)
	clojure.main/repl/read-eval-print--6588/fn--6591 (main.clj:259)
	clojure.main/repl/read-eval-print--6588 (main.clj:259)
	clojure.main/repl/fn--6597 (main.clj:277)
	clojure.main/repl (main.clj:277)
	clojure.main/repl-opt (main.clj:343)
	clojure.main/main (main.clj:441)
nil
