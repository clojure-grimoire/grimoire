user> (clojure.string/blank? nil)
true

user> (clojure.string/blank? false)
true

user> (clojure.string/blank? "   ")
true

user> (clojure.string/blank? " a ")
false