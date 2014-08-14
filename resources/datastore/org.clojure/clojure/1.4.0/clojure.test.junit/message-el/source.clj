(defn message-el
  [tag message expected-str actual-str]
  (indent)
  (start-element tag false (if message {:message message} {}))
  (element-content
   (let [[file line] (t/file-position 5)
         detail (apply str (interpose
                            "\n"
                            [(str "expected: " expected-str)
                             (str "  actual: " actual-str)
                             (str "      at: " file ":" line)]))]
     (if message (str message "\n" detail) detail)))
  (finish-element tag false)
  (println))