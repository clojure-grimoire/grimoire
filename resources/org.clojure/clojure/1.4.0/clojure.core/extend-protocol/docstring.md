  Useful when you want to provide several implementations of the same
  protocol all at once. Takes a single protocol and the implementation
  of that protocol for one or more types. Expands into calls to
  extend-type:

  (extend-protocol Protocol
    AType
      (foo [x] ...)
      (bar [x y] ...)
    BType
      (foo [x] ...)
      (bar [x y] ...)
    AClass
      (foo [x] ...)
      (bar [x y] ...)
    nil
      (foo [x] ...)
      (bar [x y] ...))

  expands into:

  (do
   (clojure.core/extend-type AType Protocol 
     (foo [x] ...) 
     (bar [x y] ...))
   (clojure.core/extend-type BType Protocol 
     (foo [x] ...) 
     (bar [x y] ...))
   (clojure.core/extend-type AClass Protocol 
     (foo [x] ...) 
     (bar [x y] ...))
   (clojure.core/extend-type nil Protocol 
     (foo [x] ...) 
     (bar [x y] ...)))