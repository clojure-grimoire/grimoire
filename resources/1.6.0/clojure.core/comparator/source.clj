(defn comparator
  "Returns an implementation of java.util.Comparator based upon pred."
  {:added "1.0"
   :static true}
  [pred]
    (fn [x y]
      (cond (pred x y) -1 (pred y x) 1 :else 0)))