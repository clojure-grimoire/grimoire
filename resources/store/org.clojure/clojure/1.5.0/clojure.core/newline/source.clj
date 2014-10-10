(defn newline
  "Writes a platform-specific newline to *out*"
  {:added "1.0"
   :static true}
  []
    (. *out* (append system-newline))
    nil)