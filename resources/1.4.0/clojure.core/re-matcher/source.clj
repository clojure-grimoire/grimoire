(defn re-matcher
  "Returns an instance of java.util.regex.Matcher, for use, e.g. in
  re-find."
  {:tag java.util.regex.Matcher
   :added "1.0"
   :static true}
  [^java.util.regex.Pattern re s]
    (. re (matcher s)))