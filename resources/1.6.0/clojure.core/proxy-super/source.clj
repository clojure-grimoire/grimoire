(defmacro proxy-super 
  "Use to call a superclass method in the body of a proxy method. 
  Note, expansion captures 'this"
  {:added "1.0"}
  [meth & args]
 `(proxy-call-with-super (fn [] (. ~'this ~meth ~@args))  ~'this ~(name meth)))