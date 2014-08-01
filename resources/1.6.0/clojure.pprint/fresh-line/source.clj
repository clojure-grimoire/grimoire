(defn fresh-line
  "Make a newline if *out* is not already at the beginning of the line. If *out* is
not a pretty writer (which keeps track of columns), this function always outputs a newline."
  {:added "1.2"}
  []
  (if (instance? clojure.lang.IDeref *out*)
    (if (not (= 0 (get-column (:base @@*out*))))
      (prn))
    (prn)))