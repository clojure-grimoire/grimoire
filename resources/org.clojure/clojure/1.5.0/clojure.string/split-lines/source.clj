(defn split-lines
  "Splits s on \\n or \\r\\n."
  {:added "1.2"}
  [^CharSequence s]
  (split s #"\r?\n"))