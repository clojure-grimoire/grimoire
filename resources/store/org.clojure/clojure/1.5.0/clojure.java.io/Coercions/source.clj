(defprotocol ^{:added "1.2"} Coercions
  "Coerce between various 'resource-namish' things."
  (^{:tag java.io.File, :added "1.2"} as-file [x] "Coerce argument to a file.")
  (^{:tag java.net.URL, :added "1.2"} as-url [x] "Coerce argument to a URL."))