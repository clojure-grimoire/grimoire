(defn print-tap-pass
  "Prints a TAP 'ok' line.  msg is a string, with no line breaks"
  {:added "1.1"}
  [msg]
  (println "ok" msg))