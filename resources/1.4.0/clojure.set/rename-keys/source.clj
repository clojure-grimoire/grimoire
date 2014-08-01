(defn rename-keys
  "Returns the map with the keys in kmap renamed to the vals in kmap"
  {:added "1.0"}
  [map kmap]
    (reduce 
     (fn [m [old new]]
       (if (and (not= old new)
                (contains? m old))
         (-> m (assoc new (get m old)) (dissoc old))
         m)) 
     map kmap))