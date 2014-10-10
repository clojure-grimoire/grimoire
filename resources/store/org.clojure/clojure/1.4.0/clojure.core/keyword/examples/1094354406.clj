;; (keyword name): name can be string, symbol, or keyword.
;; 
;; (keyword ns name): ns and name must both be string.
;; 
;; A keyword string, like a symbol, begins with a non-numeric
;; character and can contain alphanumeric characters and *, +, !, -,
;; _, and ?.  (see http://clojure.org/reader for details).
;; 
;; keyword does not validate input strings for ns and name, and may
;; return improper keywords with undefined behavior for non-conformant
;; ns and name.

user=> (keyword 'foo)
:foo

user=> (keyword "foo")       
:foo

user=> (keyword "user" "foo")
:user/foo

;; keyword in current namespace
user=> (keyword (str *ns*) "foo")
:user/foo