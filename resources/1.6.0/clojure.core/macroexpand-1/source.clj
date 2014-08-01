(defn macroexpand-1
  "If form represents a macro form, returns its expansion,
  else returns form."
  {:added "1.0"
   :static true}
  [form]
    (. clojure.lang.Compiler (macroexpand1 form)))