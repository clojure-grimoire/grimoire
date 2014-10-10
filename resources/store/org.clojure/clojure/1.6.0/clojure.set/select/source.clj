(defn select
  "Returns a set of the elements for which pred is true"
  {:added "1.0"}
  [pred xset]
    (reduce (fn [s k] (if (pred k) s (disj s k)))
            xset xset))