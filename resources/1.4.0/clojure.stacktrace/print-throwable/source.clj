(defn print-throwable
  "Prints the class and message of a Throwable."
  {:added "1.1"}
  [tr]
  (printf "%s: %s" (.getName (class tr)) (.getMessage tr)))