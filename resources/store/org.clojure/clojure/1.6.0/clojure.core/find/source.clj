(defn find
  "Returns the map entry for key, or nil if key not present."
  {:added "1.0"
   :static true}
  [map key] (. clojure.lang.RT (find map key)))