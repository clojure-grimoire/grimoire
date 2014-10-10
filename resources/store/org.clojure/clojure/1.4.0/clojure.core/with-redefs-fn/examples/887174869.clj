=> (defn f [] false)

=> (println (f))
;; false

=> (with-redefs-fn {#'f (fn [] true)} 
     #(println (f)))
;; true