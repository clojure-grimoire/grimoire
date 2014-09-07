(defn print-ctor [o print-args ^Writer w]
  (.write w "#=(")
  (.write w (.getName ^Class (class o)))
  (.write w ". ")
  (print-args o w)
  (.write w ")"))