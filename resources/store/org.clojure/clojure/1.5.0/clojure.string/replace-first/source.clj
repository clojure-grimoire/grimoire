(defn ^String replace-first
  "Replaces the first instance of match with replacement in s.

   match/replacement can be:

   char / char
   string / string
   pattern / (string or function of match).

   See also replace.

   The replacement is literal (i.e. none of its characters are treated
   specially) for all cases above except pattern / string.

   For pattern / string, $1, $2, etc. in the replacement string are
   substituted with the string that matched the corresponding
   parenthesized group in the pattern.  If you wish your replacement
   string r to be used literally, use (re-quote-replacement r) as the
   replacement argument.  See also documentation for
   java.util.regex.Matcher's appendReplacement method.

   Example:
   (clojure.string/replace-first \"swap first two words\"
                                 #\"(\\w+)(\\s+)(\\w+)\" \"$3$2$1\")
   -> \"first swap two words\""
  {:added "1.2"}
  [^CharSequence s match replacement]
  (let [s (.toString s)]
    (cond
     (instance? Character match)
     (replace-first-char s match replacement)
     (instance? CharSequence match)
     (replace-first-str s (.toString ^CharSequence match)
                        (.toString ^CharSequence replacement))
     (instance? Pattern match)
     (if (instance? CharSequence replacement)
       (.replaceFirst (re-matcher ^Pattern match s)
                      (.toString ^CharSequence replacement))
       (replace-first-by s match replacement))
     :else (throw (IllegalArgumentException. (str "Invalid match arg: " match))))))