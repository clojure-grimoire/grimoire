(defn future-cancel
  "Cancels the future, if possible."
  {:added "1.1"
   :static true}
  [^java.util.concurrent.Future f] (.cancel f true))