;; Returns a symbol with the given namespace and name.
;;
;; (symbol name): name can be a string or a symbol.
;;
;; (symbol ns name): ns and name must both be strings.
;;
;; A symbol string begins with a non-numeric character and can contain
;; alphanumeric characters and *, +, !, -, _, and ?.  (see
;; http://clojure.org/reader for details).
;;
;; symbol does not validate input strings for ns and name, and may return
;; improper symbols with undefined behavior for non-conformant ns and
;; name.

user=> (symbol 'foo)
foo

user=> (symbol "foo")
foo

user=> (symbol "clojure.core" "foo")
clojure.core/foo
