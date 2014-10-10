(defn ^String trimr
  "Removes whitespace from the right side of string."
  {:added "1.2"}
  [^CharSequence s]
  (loop [index (.length s)]
    (if (zero? index)
      ""
      (if (Character/isWhitespace (.charAt s (dec index)))
        (recur (dec index))
        (.. s (subSequence 0 index) toString)))))