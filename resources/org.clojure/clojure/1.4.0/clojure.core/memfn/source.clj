(defmacro memfn
  "Expands into code that creates a fn that expects to be passed an
  object and any args and calls the named instance method on the
  object passing the args. Use when you want to treat a Java method as
  a first-class fn."
  {:added "1.0"}
  [name & args]
  `(fn [target# ~@args]
     (. target# (~name ~@args))))