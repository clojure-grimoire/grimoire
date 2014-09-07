; Flatten a map, consing keys on to each nested vector 
(mapcat (fn [[k vs]] (map (partial cons k) vs)) {:foo [[1 2] [3 2]] :bar [[3 1]]})
;=> ((:foo 1 2) (:foo 3 2) (:bar 3 1))
