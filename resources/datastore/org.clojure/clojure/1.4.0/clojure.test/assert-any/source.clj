(defn assert-any
  "Returns generic assertion code for any test, including macros, Java
  method calls, or isolated symbols."
  {:added "1.1"}
  [msg form]
  `(let [value# ~form]
     (if value#
       (do-report {:type :pass, :message ~msg,
                :expected '~form, :actual value#})
       (do-report {:type :fail, :message ~msg,
                :expected '~form, :actual value#}))
     value#))