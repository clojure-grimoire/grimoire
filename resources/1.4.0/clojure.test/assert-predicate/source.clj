(defn assert-predicate
  "Returns generic assertion code for any functional predicate.  The
  'expected' argument to 'report' will contains the original form, the
  'actual' argument will contain the form with all its sub-forms
  evaluated.  If the predicate returns false, the 'actual' form will
  be wrapped in (not...)."
  {:added "1.1"}
  [msg form]
  (let [args (rest form)
        pred (first form)]
    `(let [values# (list ~@args)
           result# (apply ~pred values#)]
       (if result#
         (do-report {:type :pass, :message ~msg,
                  :expected '~form, :actual (cons ~pred values#)})
         (do-report {:type :fail, :message ~msg,
                  :expected '~form, :actual (list '~'not (cons '~pred values#))}))
       result#)))