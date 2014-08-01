(defn start-element
  [tag pretty & [attrs]]
  (if pretty (indent))
  (print (str "<" tag))
  (if (seq attrs)
    (doseq [[key value] attrs]
      (print (str " " (name key) "=\"" (escape-xml value) "\""))))
  (print ">")
  (if pretty (println))
  (set! *depth* (inc *depth*)))