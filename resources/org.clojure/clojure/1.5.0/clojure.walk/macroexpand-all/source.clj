(defn macroexpand-all
  "Recursively performs all possible macroexpansions in form."
  {:added "1.1"}
  [form]
  (prewalk (fn [x] (if (seq? x) (macroexpand x) x)) form))