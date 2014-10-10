(defn set-pprint-dispatch  
  "Set the pretty print dispatch function to a function matching (fn [obj] ...)
where obj is the object to pretty print. That function will be called with *out* set
to a pretty printing writer to which it should do its printing.

For example functions, see simple-dispatch and code-dispatch in 
clojure.pprint.dispatch.clj."
  {:added "1.2"}
  [function]
  (let [old-meta (meta #'*print-pprint-dispatch*)]
    (alter-var-root #'*print-pprint-dispatch* (constantly function))
    (alter-meta! #'*print-pprint-dispatch* (constantly old-meta)))
  nil)