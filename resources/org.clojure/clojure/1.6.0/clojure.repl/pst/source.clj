(defn pst
  "Prints a stack trace of the exception, to the depth requested. If none supplied, uses the root cause of the
  most recent repl exception (*e), and a depth of 12."
  {:added "1.3"}
  ([] (pst 12))
  ([e-or-depth]
     (if (instance? Throwable e-or-depth)
       (pst e-or-depth 12)
       (when-let [e *e]
         (pst (root-cause e) e-or-depth))))
  ([^Throwable e depth]
     (binding [*out* *err*]
       (println (str (-> e class .getSimpleName) " "
                     (.getMessage e)
                     (when-let [info (ex-data e)] (str " " (pr-str info)))))
       (let [st (.getStackTrace e)
             cause (.getCause e)]
         (doseq [el (take depth
                          (remove #(#{"clojure.lang.RestFn" "clojure.lang.AFn"} (.getClassName %))
                                  st))]
           (println (str \tab (stack-element-str el))))
         (when cause
           (println "Caused by:")
           (pst cause (min depth
                           (+ 2 (- (count (.getStackTrace cause))
                                   (count st))))))))))