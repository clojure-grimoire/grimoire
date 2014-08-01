(defn replace
  "Given a map of replacement pairs and a vector/collection, returns a
  vector/seq with any elements = a key in smap replaced with the
  corresponding val in smap"
  {:added "1.0"
   :static true}
  [smap coll]
    (if (vector? coll)
      (reduce1 (fn [v i]
                (if-let [e (find smap (nth v i))]
                        (assoc v i (val e))
                        v))
              coll (range (count coll)))
      (map #(if-let [e (find smap %)] (val e) %) coll)))