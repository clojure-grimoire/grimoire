user> (clojure.set/subset? #{2 3} #{1 2 3 4})
true

user> (clojure.set/subset? #{2 4} #{1 2 3 4})
true

user> (clojure.set/subset? #{2 5} #{1 2 3 4})
false