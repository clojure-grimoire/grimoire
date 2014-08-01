(defn apropos
  "Given a regular expression or stringable thing, return a seq of
all definitions in all currently-loaded namespaces that match the
str-or-pattern."
  [str-or-pattern]
  (let [matches? (if (instance? java.util.regex.Pattern str-or-pattern)
                   #(re-find str-or-pattern (str %))
                   #(.contains (str %) (str str-or-pattern)))]
    (mapcat (fn [ns]
              (filter matches? (keys (ns-publics ns))))
            (all-ns))))