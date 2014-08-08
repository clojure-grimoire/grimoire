  Repeatedly copies expr (in a do block) for each group of arguments
  in values.  values are automatically partitioned by the number of
  arguments in argv, an argument vector as in defn.

  Example: (macroexpand '(do-template [x y] (+ y x) 2 4 3 5))
           ;=> (do (+ 4 2) (+ 5 3))