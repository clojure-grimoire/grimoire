  A protocol is a named set of named methods and their signatures:
  (defprotocol AProtocolName

    ;optional doc string
    "A doc string for AProtocol abstraction"

  ;method signatures
    (bar [this a b] "bar docs")
    (baz [this a] [this a b] [this a b c] "baz docs"))

  No implementations are provided. Docs can be specified for the
  protocol overall and for each method. The above yields a set of
  polymorphic functions and a protocol object. All are
  namespace-qualified by the ns enclosing the definition The resulting
  functions dispatch on the type of their first argument, which is
  required and corresponds to the implicit target object ('this' in 
  Java parlance). defprotocol is dynamic, has no special compile-time 
  effect, and defines no new types or classes. Implementations of 
  the protocol methods can be provided using extend.

  defprotocol will automatically generate a corresponding interface,
  with the same name as the protocol, i.e. given a protocol:
  my.ns/Protocol, an interface: my.ns.Protocol. The interface will
  have methods corresponding to the protocol functions, and the
  protocol will automatically work with instances of the interface.

  Note that you should not use this interface with deftype or
  reify, as they support the protocol directly:

  (defprotocol P 
    (foo [this]) 
    (bar-me [this] [this y]))

  (deftype Foo [a b c] 
   P
    (foo [this] a)
    (bar-me [this] b)
    (bar-me [this y] (+ c y)))
  
  (bar-me (Foo. 1 2 3) 42)
  => 45

  (foo 
    (let [x 42]
      (reify P 
        (foo [this] 17)
        (bar-me [this] x)
        (bar-me [this y] x))))
  => 17