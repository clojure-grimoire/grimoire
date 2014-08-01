;; Imports only the split function from clojure.string.
user=> (use '[clojure.string :only (split)])
nil

;; split is now available without a namespace qualification.
user=> (split "hello world" #" ")
["hello" "world"]

;; You can also add the :as keyword to import the rest of clojure.string
;; with a namespace qualification.
user=> (use '[clojure.string :as s :only (split)])
nil

;; Now we can access any function in clojure.string using s.
user=> (s/replace "foobar" "foo" "squirrel")
"squirrelbar"

;; And we can still call split with or without the s qualification.
user=> (split "hello world" #" ")
["hello" "world"]
user=> (s/split "hello world" #" ")
["hello" "world"]