;; The distinction is that re-find tries to find _any part_ of the string
;; that matches the pattern, but re-matches only matches if the _entire_
;; string matches the pattern.
user=> (re-matches #"hello" "hello, world")
nil

user=> (re-matches #"hello.*" "hello, world")
"hello, world"

user=> (re-matches #"hello, (.*)" "hello, world")
["hello, world" "world"]
