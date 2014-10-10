;;just from the doc

(binding [*read-eval* false] (read-string "#=(eval (def x 3))"))
=> EvalReader not allowed when *read-eval* is false.
  [Thrown class java.lang.RuntimeException]

;;remove the anonymous function:

(binding [*read-eval* false] (read-string "(def x 3)"))
=> (def x 3)

;;which is evaluable

(eval (binding [*read-eval* false] (read-string "(def x 3)")))
=> #'user/x

x
=>3