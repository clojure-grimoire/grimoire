=> *clojure-version*
{:major 1, :minor 5, :incremental 0, :qualifier "RC17"}
=> var
;CompilerException java.lang.RuntimeException: Unable to resolve symbol: var in this context, compiling:(NO_SOURCE_PATH:1:42) 
=> (var)
;CompilerException java.lang.NullPointerException, compiling:(NO_SOURCE_PATH:1:1) 
=> (var 1)
;CompilerException java.lang.ClassCastException: java.lang.Long cannot be cast to clojure.lang.Symbol, compiling:(NO_SOURCE_PATH:1:1) 
=> (var defn)
#'clojure.core/defn
=> (var defn 1 2 3 4)
#'clojure.core/defn
