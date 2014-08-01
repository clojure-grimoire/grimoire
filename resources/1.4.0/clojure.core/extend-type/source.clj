(defmacro extend-type 
  "A macro that expands into an extend call. Useful when you are
  supplying the definitions explicitly inline, extend-type
  automatically creates the maps required by extend.  Propagates the
  class as a type hint on the first argument of all fns.

  (extend-type MyType 
    Countable
      (cnt [c] ...)
    Foo
      (bar [x y] ...)
      (baz ([x] ...) ([x y & zs] ...)))

  expands into:

  (extend MyType
   Countable
     {:cnt (fn [c] ...)}
   Foo
     {:baz (fn ([x] ...) ([x y & zs] ...))
      :bar (fn [x y] ...)})"
  {:added "1.2"} 
  [t & specs]
  (emit-extend-type t specs))