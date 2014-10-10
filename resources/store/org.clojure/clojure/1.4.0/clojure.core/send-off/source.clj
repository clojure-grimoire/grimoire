(defn send-off
  "Dispatch a potentially blocking action to an agent. Returns the
  agent immediately. Subsequently, in a separate thread, the state of
  the agent will be set to the value of:

  (apply action-fn state-of-agent args)"
  {:added "1.0"
   :static true}
  [^clojure.lang.Agent a f & args]
  (.dispatch a (binding [*agent* a] (binding-conveyor-fn f)) args true))