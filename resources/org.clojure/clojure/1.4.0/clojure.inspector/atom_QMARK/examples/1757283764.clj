;; atom? returns true if the form passed does not 
;; implement IPersistentCollection. The atom referred 
;; here is not the atom used in managing mutable state  

user=> (use `[clojure.inspector :include (atom?)])

user=> (atom? 1)
true

user=> (atom? \a)
true

user=> (atom? "hello world")
true

user=> (atom? :keyword)
true

user=> (atom? nil)
true

user=> (atom? '())
false

user=> (atom? [1, 3, 5])
false

user=> (atom? #{\a \e \i \o \u})
false

user=> (atom? {:x 16 :y 25})
false