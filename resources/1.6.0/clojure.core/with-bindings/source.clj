(defmacro with-bindings
  "Takes a map of Var/value pairs. Installs for the given Vars the associated
  values as thread-local bindings. The executes body. Pops the installed
  bindings after body was evaluated. Returns the value of body."
  {:added "1.1"}
  [binding-map & body]
  `(with-bindings* ~binding-map (fn [] ~@body)))