user=> (binding [*print-meta* true] 
         (pr (var defmacro)) )
;;^{:macro true, :ns #<Namespace clojure.core>, :name defmacro, :arglists ^{:line 424, :column 15} ([name doc-string? attr-map? [params*] body] [name doc-string? attr-map? ^{:line 425, :column 46} ([params*] body) + attr-map?]), :column 1, :added "1.0", :doc "Like defn, but the resulting function name is declared as a\n  macro and will be used as a macro by the compiler when it is\n  called.", :line 419, :file "clojure/core.clj"} #'clojure.core/defmacro
nil
