(defn key
  "Returns the key of the map entry."
  {:added "1.0"
   :static true}
  [^java.util.Map$Entry e]
    (. e (getKey)))