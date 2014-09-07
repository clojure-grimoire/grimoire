(defn ^String lower-case
  "Converts string to all lower-case."
  {:added "1.2"}
  [^CharSequence s]
  (.. s toString toLowerCase))