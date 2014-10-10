(defmacro with-loading-context [& body]
  `((fn loading# [] 
        (. clojure.lang.Var (pushThreadBindings {clojure.lang.Compiler/LOADER  
                                                 (.getClassLoader (.getClass ^Object loading#))}))
        (try
         ~@body
         (finally
          (. clojure.lang.Var (popThreadBindings)))))))