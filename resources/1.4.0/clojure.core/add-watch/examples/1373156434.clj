;; Add useful context to watcher function:
(defn watch-agent [_agent context]
    (let [watch-fn (fn [_context _key _ref old-value new-value] ;...
               )] 
        (add-watch _agent nil (partial watch-fn context))))
