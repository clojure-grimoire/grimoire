(defn function?
  "Returns true if argument is a function or a symbol that resolves to
  a function (not a macro)."
  {:added "1.1"}
  [x]
  (if (symbol? x)
    (when-let [v (resolve x)]
      (when-let [value (get-possibly-unbound-var v)]
        (and (fn? value)
             (not (:macro (meta v))))))
    (fn? x)))