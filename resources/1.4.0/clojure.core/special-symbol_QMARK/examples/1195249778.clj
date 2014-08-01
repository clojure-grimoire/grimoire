user=> (clojure-version)
"1.4.0"
;; the set of special symbols for this clojure-version are the following:
user=> (keys (. clojure.lang.Compiler specials))
(deftype* new quote & var set! monitor-enter recur . case* clojure.core/import* reify* do fn* throw monitor-exit letfn* finally let* loop* try catch if def)

;; for example, "def" is not a function, not a macro, not even a var, but a special form:
user=> (fn? 'def)
false
user=> (:macro (meta (find-var 'clojure.core/def)))
nil
user=> (find-var 'clojure.core/def)
nil
user=> (special-symbol? 'def)
true

;; while "defn" is not a special form but a macro:
user=> (special-symbol? 'defn)
false
user=> (:macro (meta (find-var 'clojure.core/defn)))
true
