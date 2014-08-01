(defn cycle
  "Returns a lazy (infinite!) sequence of repetitions of the items in coll."
  {:added "1.0"
   :static true}
  [coll] (lazy-seq 
          (when-let [s (seq coll)] 
              (concat s (cycle s)))))