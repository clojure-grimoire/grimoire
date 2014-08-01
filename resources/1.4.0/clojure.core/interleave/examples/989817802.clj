(def s1 [[:000-00-0000 "TYPE 1" "JACKSON" "FRED"]
         [:000-00-0001 "TYPE 2" "SIMPSON" "HOMER"]
         [:000-00-0002 "TYPE 4" "SMITH" "SUSAN"]])

(interleave (map #(nth % 0 nil) s1) (map #(nth % 1 nil) s1))