(defn pprint 
  "Pretty print object to the optional output writer. If the writer is not provided, 
print the object to the currently bound value of *out*."
  {:added "1.2"}
  ([object] (pprint object *out*)) 
  ([object writer]
     (with-pretty-writer writer
       (binding [*print-pretty* true]
         (binding-map (if (or (not (= *print-base* 10)) *print-radix*) {#'pr pr-with-base} {}) 
           (write-out object)))
       (if (not (= 0 (get-column *out*)))
         (prn)))))