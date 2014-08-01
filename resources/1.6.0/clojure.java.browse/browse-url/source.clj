(defn browse-url
  "Open url in a browser"
  {:added "1.2"}
  [url]
  (let [script @*open-url-script*
        script (if (= :uninitialized script)
                 (reset! *open-url-script* (open-url-script-val))
                 script)]
    (or (when script (sh/sh script (str url)) true)
        (open-url-in-browser url)
        (open-url-in-swing url))))