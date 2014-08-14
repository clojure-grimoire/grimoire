  When set to logical false, the EvalReader (#=(...)) is disabled in the 
  read/load in the thread-local binding.
  Example: (binding [*read-eval* false] (read-string "#=(eval (def x 3))"))

  Defaults to true