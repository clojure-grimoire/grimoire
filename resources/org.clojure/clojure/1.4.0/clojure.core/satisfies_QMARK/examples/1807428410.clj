(ns foo)

(defprotocol Foo
  (foo [this]))

(defprotocol Bar
  (bar [this]))

(extend java.lang.Number
  Bar
  {:bar (fn [this] 42)})

(extend java.lang.String
  Foo
  {:foo (fn [this] "foo")}
  Bar
  {:bar (fn [this] "forty two")})

(satisfies? Foo "zam") ; => true
(satisfies? Bar "zam") ; => true
(satisfies? Foo 123)   ; => false
(satisfies? Bar 123)   ; => true