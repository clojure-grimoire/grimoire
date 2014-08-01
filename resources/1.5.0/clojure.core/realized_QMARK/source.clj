(defn realized?
  "Returns true if a value has been produced for a promise, delay, future or lazy sequence."
  {:added "1.3"}
  [^clojure.lang.IPending x] (.isRealized x))