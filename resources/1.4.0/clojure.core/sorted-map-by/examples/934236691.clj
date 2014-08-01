;; If you wish to sort the map according to the values, instead of by keys 
;; the following code WILL NOT WORK! This is because the map values are not unique.

user=> (let [results {:A 1 :B 2 :C 2 :D 5 :E 1 :F 1}]
  (into (sorted-map-by (fn [key1 key2]
                         (compare (get results key2)
                                  (get results key1))))
        results))

=> {:D 5, :C 2, :A 1}

;; To make sure that the sorting works, we can make sure that the comparator 
;; works on unique values

user=> (let [results {:A 1 :B 2 :C 2 :D 5 :E 1 :F 1}]
  (into (sorted-map-by (fn [key1 key2]
                         (compare [(get results key2) key2]
                                  [(get results key1) key1])))
        results))

=> {:D 5, :C 2, :B 2, :F 1, :E 1, :A 1}