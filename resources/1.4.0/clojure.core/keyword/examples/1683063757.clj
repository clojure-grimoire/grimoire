;; some gotchas to be aware of:

user=> (keyword "user" 'abc)
ClassCastException clojure.lang.Symbol cannot be cast to java.lang.String  clojure.core/keyword (core.clj:558)

user=> (keyword *ns* "abc")
ClassCastException clojure.lang.Namespace cannot be cast to java.lang.String  clojure.core/keyword (core.clj:558)

user=> (keyword 'user "abc")
ClassCastException clojure.lang.Symbol cannot be cast to java.lang.String  clojure.core/keyword (core.clj:558)


;; Warning - the following generated keywords are non-conformant and may wreak
;; serious havoc in the near/far future when least expected...

user=> (keyword "abc def")
:abc def

user=> (keyword "123def")
:123def

user=> (keyword "/abc/def/ghi")
:/abc/def/ghi