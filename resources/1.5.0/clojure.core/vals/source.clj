(defn vals
  "Returns a sequence of the map's values."
  {:added "1.0"
   :static true}
  [map] (. clojure.lang.RT (vals map)))