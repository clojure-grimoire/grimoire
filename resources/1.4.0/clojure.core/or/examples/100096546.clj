user> (or true false false)
true

user> (or true true true)
true

user> (or false false false)
false

user> (or nil nil)
nil

user> (or false nil)
nil

user> (or true nil)
true

;; or doesn't evaluate if the first value is true
user> (or true (println "foo"))
true

;; order matters
user> (or (println "foo") true)
foo
true