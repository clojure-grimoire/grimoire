(defn val
  "Returns the value in the map entry."
  {:added "1.0"
   :static true}
  [^java.util.Map$Entry e]
    (. e (getValue)))