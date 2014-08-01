(def x [{:foo 2 :bar 11}
 {:bar 99 :foo 1}
 {:bar 55 :foo 2}
 {:foo 1 :bar 77}])
; sort-by given key order (:bar)
(def order [55 77 99 11])
(sort-by 
  #((into {} (map-indexed (fn [i e] [e i]) order)) (:bar %)) 
  x)
;=> ({:bar 55, :foo 2} {:foo 1, :bar 77} {:bar 99, :foo 1} {:foo 2, :bar 11})