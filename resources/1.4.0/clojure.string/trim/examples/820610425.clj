;; Trim basically does what you'd expect.  What the doc string
;; does not tell you however is that:
;;  - null will cause an error
;;  - non-string parameters will be converted to a string
;;    before being trimed.

(use 'clojure.string)
user=> (trim "     a      ")
"a"
user=> (trim nil)  
java.lang.NullPointerException (NO_SOURCE_FILE:0)
user=> (trim 1.1)
"1.1"
user=> (trim [1 2 3])
"[1 2 3]"
