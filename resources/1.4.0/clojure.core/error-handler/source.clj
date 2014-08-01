(defn error-handler
  "Returns the error-handler of agent a, or nil if there is none.
  See set-error-handler!"
  {:added "1.2"
   :static true}
  [^clojure.lang.Agent a]
  (.getErrorHandler a))