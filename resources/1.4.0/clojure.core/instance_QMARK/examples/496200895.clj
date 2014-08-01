;; Some things are more than what they seem to be at first glance
user=> (instance? clojure.lang.IFn +)
true
user=> (instance? clojure.lang.Keyword :a)
true
user=> (instance? clojure.lang.IFn :a)
true
user=> (instance? clojure.lang.IFn {:a 1})
true
