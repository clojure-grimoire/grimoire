=> *clojure-version*
{:major 1, :minor 5, :incremental 0, :qualifier "RC17"}
=> var?
#<core$var_QMARK_ clojure.core$var_QMARK_@669251cc>
=> (var?)
;ArityException Wrong number of args (0) passed to: core$var-QMARK-  clojure.lang.AFn.throwArity (AFn.java:437)
=> (var? 1)
false
=> (var? defn)
;CompilerException java.lang.RuntimeException: Can't take value of a macro: #'clojure.core/defn, compiling:(NO_SOURCE_PATH:1:1) 
=> (var? #'defn)
true
=> (var? #'defn 1 2 3 4)
;ArityException Wrong number of args (5) passed to: core$var-QMARK-  clojure.lang.AFn.throwArity (AFn.java:437)
=> (var? (var defn))
true
=> (var? apply)
false
=> (var? #'apply)
true
