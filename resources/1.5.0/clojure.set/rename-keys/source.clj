(defn rename-keys
  "Returns the map with the keys in kmap renamed to the vals in kmap"
  {:added "1.0"}
  [map kmap]
    (reduce 
     (fn [m [old new]]
       (if (contains? map old)
         (assoc m new (get map old))
         m)) 
     (apply dissoc map (keys kmap)) kmap))