(defn println
  "Same as print followed by (newline)"
  {:added "1.0"
   :static true}
  [& more]
    (binding [*print-readably* nil]
      (apply prn more)))