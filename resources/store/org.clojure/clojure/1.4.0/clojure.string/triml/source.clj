(defn ^String triml
  "Removes whitespace from the left side of string."
  {:added "1.2"}
  [^CharSequence s]
  (loop [index (int 0)]
    (if (= (.length s) index)
      ""
      (if (Character/isWhitespace (.charAt s index))
        (recur (inc index))
        (.. s (subSequence index (.length s)) toString)))))