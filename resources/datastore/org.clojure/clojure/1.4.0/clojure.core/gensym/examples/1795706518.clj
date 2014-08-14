;; syntax-reader uses gensym for non-namespace-qualified symbols ending with '#'
;; http://clojure.org/reader

user=> `(name0#)       ; gensym, form is useful in defmacro
(name0__1206__auto__)

user=> `(user/name1#)  ; no gensym, namespace-qualified
(user/name1#)

user=> `(:key0#)       ; no gensym, keyword
(:key0#)

user=> `(::key1#)      ; no gensym, keyword
(:user/key1#)
