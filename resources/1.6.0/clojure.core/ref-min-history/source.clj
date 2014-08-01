(defn ref-min-history
  "Gets the min-history of a ref, or sets it and returns the ref"
  {:added "1.1"
   :static true}
  ([^clojure.lang.Ref ref]
    (.getMinHistory ref))
  ([^clojure.lang.Ref ref n]
    (.setMinHistory ref n)))