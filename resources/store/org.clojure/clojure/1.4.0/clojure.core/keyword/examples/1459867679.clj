;; You can define namespaced keywords using '::'
user=> (def a :foo)
#'user/a

user=> (def b ::foo)
#'user/b

user=> (ns foo)
foo=> user/a
:foo

foo=> user/b
:user/foo

foo=> ::foo
:foo/foo

foo=> (= user/a :foo)
true

foo=> (= user/b ::foo)
false

foo=> (= user/b :user/foo)
true