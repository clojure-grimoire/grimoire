(defn difference
  "Return a set that is the first set without elements of the remaining sets"
  {:added "1.0"}
  ([s1] s1)
  ([s1 s2] 
     (if (< (count s1) (count s2))
       (reduce (fn [result item] 
                   (if (contains? s2 item) 
                     (disj result item) 
                     result))
               s1 s1)
       (reduce disj s1 s2)))
  ([s1 s2 & sets] 
     (reduce difference s1 (conj sets s2))))