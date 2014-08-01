(defn set-agent-send-executor!
  "Sets the ExecutorService to be used by send"
  {:added "1.5"}
  [executor]
  (set! clojure.lang.Agent/pooledExecutor executor))