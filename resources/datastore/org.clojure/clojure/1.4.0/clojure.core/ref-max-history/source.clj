(defn ref-max-history
  "Gets the max-history of a ref, or sets it and returns the ref"
  {:added "1.1"
   :static true}
  ([^clojure.lang.Ref ref]
    (.getMaxHistory ref))
  ([^clojure.lang.Ref ref n]
    (.setMaxHistory ref n)))