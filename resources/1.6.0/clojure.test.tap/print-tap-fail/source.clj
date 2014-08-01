(defn print-tap-fail 
  "Prints a TAP 'not ok' line.  msg is a string, with no line breaks"
  {:added "1.1"}
  [msg]
  (println "not ok" msg))