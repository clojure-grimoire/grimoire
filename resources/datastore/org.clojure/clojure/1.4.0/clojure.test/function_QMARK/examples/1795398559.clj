user> (defn foo [] (println "foo"))
#'user/foo

user> (def bar "bar")
#'user/bar

user> (clojure.test/function? foo)
true

user> (clojure.test/function? bar)
false