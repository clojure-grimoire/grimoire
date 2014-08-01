(defn ref-history-count
  "Returns the history count of a ref"
  {:added "1.1"
   :static true}
  [^clojure.lang.Ref ref]
    (.getHistoryCount ref))