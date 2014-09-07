(defn get-validator
  "Gets the validator-fn for a var/ref/agent/atom."
  {:added "1.0"
   :static true}
 [^clojure.lang.IRef iref] (. iref (getValidator)))