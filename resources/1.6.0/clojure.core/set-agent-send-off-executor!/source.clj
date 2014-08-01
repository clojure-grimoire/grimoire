(defn set-agent-send-off-executor!
  "Sets the ExecutorService to be used by send-off"
  {:added "1.5"}
  [executor]
  (set! clojure.lang.Agent/soloExecutor executor))