(defn map-invert
  "Returns the map with the vals mapped to the keys."
  {:added "1.0"}
  [m] (reduce (fn [m [k v]] (assoc m v k)) {} m))