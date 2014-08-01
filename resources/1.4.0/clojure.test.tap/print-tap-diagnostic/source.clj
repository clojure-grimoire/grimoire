(defn print-tap-diagnostic
  "Prints a TAP diagnostic line.  data is a (possibly multi-line)
  string."
  {:added "1.1"}
  [data]
  (doseq [line (.split ^String data "\n")]
    (println "#" line)))