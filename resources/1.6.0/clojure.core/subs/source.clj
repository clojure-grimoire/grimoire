(defn subs
  "Returns the substring of s beginning at start inclusive, and ending
  at end (defaults to length of string), exclusive."
  {:added "1.0"
   :static true}
  (^String [^String s start] (. s (substring start)))
  (^String [^String s start end] (. s (substring start end))))