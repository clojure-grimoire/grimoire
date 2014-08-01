(defn write-out 
  "Write an object to *out* subject to the current bindings of the printer control 
variables. Use the kw-args argument to override individual variables for this call (and 
any recursive calls).

*out* must be a PrettyWriter if pretty printing is enabled. This is the responsibility
of the caller.

This method is primarily intended for use by pretty print dispatch functions that 
already know that the pretty printer will have set up their environment appropriately.
Normal library clients should use the standard \"write\" interface. "
  {:added "1.2"}
  [object]
  (let [length-reached (and 
                        *current-length*
                        *print-length*
                        (>= *current-length* *print-length*))]
    (if-not *print-pretty*
      (pr object)
      (if length-reached
        (print "...")
        (do
          (if *current-length* (set! *current-length* (inc *current-length*)))
          (*print-pprint-dispatch* object))))
    length-reached))