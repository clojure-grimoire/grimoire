(defn ^String replace
  "Replaces all instance of match with replacement in s.

   match/replacement can be:

   string / string
   char / char
   pattern / (string or function of match).

   See also replace-first."
  {:added "1.2"}
  [^CharSequence s match replacement]
  (let [s (.toString s)]
    (cond 
     (instance? Character match) (.replace s ^Character match ^Character replacement)
     (instance? CharSequence match) (.replace s ^CharSequence match ^CharSequence replacement)
     (instance? Pattern match) (if (instance? CharSequence replacement)
                                 (.replaceAll (re-matcher ^Pattern match s)
                                              (.toString ^CharSequence replacement))
                                 (replace-by s match replacement))
     :else (throw (IllegalArgumentException. (str "Invalid match arg: " match))))))