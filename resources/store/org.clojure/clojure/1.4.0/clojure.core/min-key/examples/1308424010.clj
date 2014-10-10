; "min-key"/"max-key" to "min"/"max" like "sort-by" to "sort"
(min-key #(Math/abs %) -3 1 4)
; 1

(apply min-key #(Math/abs %) [-3 1 4])
; 1
