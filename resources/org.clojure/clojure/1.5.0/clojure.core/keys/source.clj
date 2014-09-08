(defn keys
  "Returns a sequence of the map's keys."
  {:added "1.0"
   :static true}
  [map] (. clojure.lang.RT (keys map)))