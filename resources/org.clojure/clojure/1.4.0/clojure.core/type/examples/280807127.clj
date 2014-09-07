;; Checking numbers
user=> (type 10)
java.lang.Long

user=> (type 10.0)
java.lang.Double


;; Checking collections
user=> (type [10 20])
clojure.lang.PersistentVector

user=> (type '(10 20))
clojure.lang.PersistentList


;; Checking other, but somewhat intuitive, forms
user=> (type :a)
clojure.lang.Keyword

user=> (type Thread)
java.lang.Class


;; Checking a symbol
user=> (type 'whatever)
clojure.lang.Symbol

;; A surprise attack yields
user=> (type clojure.lang.Symbol)
;; not such a surprising response
java.lang.Class


;; Checking a function
user=> (defn foo [] ("any string"))
#'user/foo
user=> (type foo)
user$foo


;; Checking a macro
user=> (type fn)
user$fn

user=> (type clojure.core/fn)
java.lang.Exception: Can't take value of a macro: #'clojure.core/fn (NO_SOURCE_FILE:94)

