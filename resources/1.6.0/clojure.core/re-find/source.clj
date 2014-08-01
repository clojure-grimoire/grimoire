(defn re-find
  "Returns the next regex match, if any, of string to pattern, using
  java.util.regex.Matcher.find().  Uses re-groups to return the
  groups."
  {:added "1.0"
   :static true}
  ([^java.util.regex.Matcher m]
   (when (. m (find))
     (re-groups m)))
  ([^java.util.regex.Pattern re s]
   (let [m (re-matcher re s)]
     (re-find m))))