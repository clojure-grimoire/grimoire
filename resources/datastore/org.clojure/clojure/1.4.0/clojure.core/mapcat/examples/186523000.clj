user=> (mapcat (fn [[k v]] 
                 (for [[k2 v2] v] 
                   (concat [k k2] v2)))
         '{:a {:x (1 2) :y (3 4)}
           :b {:x (1 2) :z (5 6)}})

((:a :x 1 2) (:a :y 3 4) (:b :x 1 2) (:b :z 5 6))