(defn char-cnt [s]
  "Counts occurence of each character in s"
  (reduce
    (fn [m k]
      (update-in m [k] (fnil inc 0)))
  {}
  (seq s)))
;Note use of fnil above - returns 0 if nil is passed to inc (avoids null pointer exception)
