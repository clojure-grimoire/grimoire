(def s1 [[:000-00-0000 "TYPE 1" "JACKSON" "FRED"]
         [:000-00-0001 "TYPE 2" "SIMPSON" "HOMER"]
         [:000-00-0002 "TYPE 4" "SMITH" "SUSAN"]])

(def cols [0 2 3])

(defn f1 
  [s1 col] 
  (map #(get-in s1 [% col] nil) (range (count s1))))

(apply interleave (map (partial f1 s1) cols))

(:000-00-0000 "JACKSON" "FRED" :000-00-0001 "SIMPSON" "HOMER" :000-00-0002 "SMITH" "SUSAN")