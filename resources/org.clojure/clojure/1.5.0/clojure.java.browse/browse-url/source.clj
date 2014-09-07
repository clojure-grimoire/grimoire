(defn browse-url
  "Open url in a browser"
  {:added "1.2"}
  [url]
  (or (open-url-in-browser url)
      (when *open-url-script* (sh/sh *open-url-script* (str url)) true)
      (open-url-in-swing url)))