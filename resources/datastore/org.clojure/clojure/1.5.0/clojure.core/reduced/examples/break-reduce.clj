; Accumulate items into the list while numbers are smaller than 5.
; Without the (reduced) call the reduce would continue consuming
; the infinite sequence from range forever.
(reduce
  (fn [acc v]
    (if (< v 5)
      (conj acc v)
      (reduced acc)))
  []
  (range))
; => [0 1 2 3 4]
