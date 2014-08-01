;; An anonymous function is a function as you'd expect
user=> (ifn? #("my anonymous function"))
true

;; Is a vector a function?
user=> (ifn? [1 2 3])
true

;; Sure is, lets call it.
user=> ([1 2 3] 0)
1

;; Maps and sets are functions, too.

;; a number is definitely not a function
user=> (ifn? 1)
false

;; but a symbol is
user=> (ifn? 'foo)
true

;; and so is a keyword
user=> (ifn? :foo)
true