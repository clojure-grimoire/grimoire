(defn ^String triml
  "Removes whitespace from the left side of string."
  {:added "1.2"}
  [^CharSequence s]
  (let [len (.length s)]
    (loop [index 0]
      (if (= len index)
        ""
        (if (Character/isWhitespace (.charAt s index))
          (recur (unchecked-inc index))
          (.. s (subSequence index len) toString))))))